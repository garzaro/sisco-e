package com.sisco.escola.service.impl;

import com.sisco.escola.api.dto.UsuarioDTO;
import com.sisco.escola.api.mapper.UsuarioMapper;
import com.sisco.escola.exception.ErroAutenticacaoException;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Mensagens;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementação de {@link UsuarioService}.
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Orquestra operações de CRUD delegando persistência ao {@link UsuarioRepository}.</li>
 *   <li>Usa {@link UsuarioMapper} (MapStruct) para converter entidades em DTOs e vice-versa.</li>
 *   <li>Codifica senhas com Argon2 antes de qualquer persistência.</li>
 *   <li>Nunca expõe a entidade JPA fora desta classe — o contrato público retorna apenas DTOs.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final MessageSource messageSource;
    private final Mensagens mensagens;

    /** Domínios de e-mail aceitos para cadastro. */
    private static final List<String> DOMINIOS_PERMITIDOS = List.of("gmail.com", "gov.br");

    /**
     * Argon2id — configuração recomendada pelo OWASP (2023):
     * saltLength=16, hashLength=32, parallelism=1, memory=64MB, iterations=3.
     */
    private final Argon2PasswordEncoder passwordEncoder =
            new Argon2PasswordEncoder(16, 32, 1, 65536, 3);

    // -------------------------------------------------------------------------
    // CRUD Essencial
    // -------------------------------------------------------------------------

    @Override
    @Transactional
    public UsuarioDTO cadastrar(UsuarioDTO usuarioDTO) {
        validarEmail(usuarioDTO.getEmail());
        validarCpf(usuarioDTO.getCpf());
        validarUsername(usuarioDTO.getUsuario());
        validarDominioEmail(usuarioDTO.getEmail());

        Usuario entity = usuarioMapper.toEntity(usuarioDTO);
        entity.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        entity.setAtivo(true);

        Usuario salvo = usuarioRepository.save(entity);
        return usuarioMapper.toDto(salvo);
    }

    @Override
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario entity = encontrarOuLancarErro(id);

        // Valida unicidade apenas se o valor for diferente do atual
        if (!entity.getEmail().equalsIgnoreCase(usuarioDTO.getEmail())) {
            validarEmail(usuarioDTO.getEmail());
        }
        if (!entity.getCpf().equals(usuarioDTO.getCpf())) {
            validarCpf(usuarioDTO.getCpf());
        }
        if (!entity.getUsuario().equalsIgnoreCase(usuarioDTO.getUsuario())) {
            validarUsername(usuarioDTO.getUsuario());
        }

        usuarioMapper.atualizarEntidadeComDto(usuarioDTO, entity);
        return usuarioMapper.toDto(entity); // save implícito pelo contexto transacional
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        return usuarioMapper.toDto(encontrarOuLancarErro(id));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        encontrarOuLancarErro(id); // garante que o registro existe antes de deletar
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toDto);
    }

    // -------------------------------------------------------------------------
    // Segurança e Identidade
    // -------------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO autenticar(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ErroAutenticacaoException("Credenciais inválidas"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new ErroAutenticacaoException(mensagens.pegar("credenciais.invalidas"));
        }
        return usuarioMapper.toDto(usuario);
    }
    
    //RecursoNaoEncontradoException depois ver ai se altera o nome da classe
    @Override
    @Transactional(readOnly = true)
    //@Timed CONTINUAR COM TIMED
    public UsuarioDTO buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDto)
                .orElseThrow(() -> new RegraDeNegocioException(
                		messageSource.getMessage("usuario.nao.encontrado", null,
                				LocaleContextHolder.getLocale())));
    }
//    "Usuário não encontrado"
    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorUsername(String username) {
        return usuarioRepository.findByUsuario(username)
                .map(usuarioMapper::toDto)
                .orElseThrow(() -> new RegraDeNegocioException(
                		mensagens.pegar("usuario.nao.encontrado")));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .map(usuarioMapper::toDto)
                .orElseThrow(() -> new RegraDeNegocioException(
                		mensagens.pegar("cpf.nao.encontrado")
                		));
    }

    @Override
    @Transactional
    public void redefinirSenha(Long id, String newPassword) {
        Usuario entity = encontrarOuLancarErro(id);
        entity.setPassword(passwordEncoder.encode(newPassword));
        // save implícito pelo contexto transacional (@Transactional + entidade gerenciada)
    }

    // -------------------------------------------------------------------------
    // Regras de Negócio — Ciclo de Vida da Conta
    // -------------------------------------------------------------------------

    @Override
    @Transactional
    public void ativarConta(Long id) {
        Usuario entity = encontrarOuLancarErro(id);
        entity.setAtivo(true);
    }

    @Override
    @Transactional
    public void desativarConta(Long id) {
        Usuario entity = encontrarOuLancarErro(id);
        entity.setAtivo(false);
    }

    // -------------------------------------------------------------------------
    // Validação de Existência
    // -------------------------------------------------------------------------

    @Override
    public void validarEmail(String email) {
        boolean existeEmail = usuarioRepository.existsByEmail(email);
        if (existeEmail) {
            throw new ErroValidacaoException(mensagens.pegar("email.ja.existe"));
        }
    }

    @Override
    public void validarCpf(String cpf) {
        boolean existeCpf =  usuarioRepository.existsByCpf(cpf);
        if (existeCpf) {
            throw new ErroValidacaoException(mensagens.pegar("cpf.ja.existe"));
        }
    }

    @Override
    public void validarUsername(String username) {
        boolean existeUsuario = usuarioRepository.existsByUsuario(username);
        if (existeUsuario) {
            throw new ErroValidacaoException(mensagens.pegar("usuario.ja.existe"));
        }
    }

    // -------------------------------------------------------------------------
    // Métodos auxiliares privados
    // -------------------------------------------------------------------------

    /**
     * Busca um usuário pelo ID ou lança {@link RegraDeNegocioException}.
     */
    private Usuario encontrarOuLancarErro(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(
                        mensagens.pegar("usuario.nao.encontrado.com.id" )));
    }

    /**
     * Valida se o domínio do e-mail está na lista de domínios permitidos.
     */
    private void validarDominioEmail(String email) {
        String dominio = email.substring(email.lastIndexOf("@") + 1);
        if (!DOMINIOS_PERMITIDOS.contains(dominio)) {
            throw new ErroValidacaoException(
                    mensagens.pegar("domínio.de.e-mail.nao.permitido" + DOMINIOS_PERMITIDOS));
        }
    }
}
