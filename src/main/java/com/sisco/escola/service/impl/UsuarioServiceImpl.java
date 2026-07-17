package com.sisco.escola.service.impl;

import com.sisco.escola.api.dto.UsuarioRequestDTO;
import com.sisco.escola.api.mapper.UsuarioMapper;
import com.sisco.escola.exception.ErroAutenticacaoException;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.exception.RegraDeNegocioException;
import com.sisco.escola.model.entity.Mensagens;
import com.sisco.escola.model.entity.Usuario;
import com.sisco.escola.model.repository.UsuarioRepository;
import com.sisco.escola.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

/**
 * Implementação de {@link UsuarioService}.
 *
 * Responsabilidades:
 *  
 * validação de estado e regras de negócio
 * Orquestra operações de CRUD delegando persistência ao {@link UsuarioRepository}.
 * Usa {@link UsuarioMapper} (MapStruct) para converter entidades em DTOs e vice-versa.
 * Codifica senhas com Argon2 antes de qualquer persistência.
 * Nunca expõe a entidade JPA fora desta classe — o contrato público retorna apenas DTOs.
 * 
 */

/**
 * Isso instrui o Spring a validar automaticamente os parâmetros de métodos de classes anotadas 
 * com @Validated usando as anotações Bean Validation.
 * 
 **/
@Validated
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final MessageSource messageSource;
    private final Mensagens mensagens;
    /**
     * Injeção do PasswordEncoder (Argon2id) configurado na classe de segurança central SecurityConfig.
     */
    private final PasswordEncoder passwordEncoder;

    /** Domínios de e-mail aceitos para cadastro. */
    private static final List<String> DOMINIOS_PERMITIDOS = List.of("gmail.com", "gov.br");

    @Override
    @Transactional
    public UsuarioRequestDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        validarEmail(usuarioRequestDTO.getEmail());
        validarCpf(usuarioRequestDTO.getCpf());
        
        Usuario entity = usuarioMapper.toEntity(usuarioRequestDTO);
        entity.setSenha(passwordEncoder.encode(usuarioRequestDTO.getSenha()));
        entity.setIsAtivo(true);

        Usuario salvo = usuarioRepository.save(entity);
        return usuarioMapper.toDto(salvo);
    }

    @Override
    @Transactional
    public UsuarioRequestDTO atualizar(UUID uuid, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario entity = encontrarOuLancarErro(uuid);
        /**valida unicidade apenas se o valor for diferente do atual, se o estado for alterado**/
        if (!entity.getEmail().equalsIgnoreCase(usuarioRequestDTO.getEmail())) {
            validarEmail(usuarioRequestDTO.getEmail());
        }
        if (!entity.getCpf().matches(usuarioRequestDTO.getCpf())) {
            validarCpf(usuarioRequestDTO.getCpf());
        }
//        if (!entity.getUsuario().equals(usuarioRequestDTO.getUsuario())) {
//            validarUsuario(usuarioRequestDTO.getUsuario());
//        }

        usuarioMapper.atualizarEntidadeDto(usuarioRequestDTO, entity);
        return usuarioMapper.toDto(entity); // save implícito pelo contexto transacional
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioRequestDTO buscarPorId(UUID id) {
        return usuarioMapper.toDto(encontrarOuLancarErro(id));
    }

    @Override
    @Transactional
    public void deletar(UUID uuid) {
        encontrarOuLancarErro(uuid); // garante que o registro existe antes de deletar
        usuarioRepository.deleteById(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioRequestDTO> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toDto);
    }

    /**Segurança e Identidade**/

    @Override
    @Transactional(readOnly = true)
    public UsuarioRequestDTO autenticar(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ErroAutenticacaoException("Credenciais inválidas!!!"));

        if (!passwordEncoder.matches(password, usuario.getSenha())) {
            throw new ErroAutenticacaoException(mensagens.pegarMensagem("credenciais.invalidas!!!"));
        }
        return usuarioMapper.toDto(usuario);
    }
    
    //RecursoNaoEncontradoException depois ver ai se altera o nome da classe
    @Override
    @Transactional(readOnly = true)
    //@Timed CONTINUAR COM TIMED
    public UsuarioRequestDTO buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDto)
                .orElseThrow(() -> new RegraDeNegocioException(
                		messageSource.getMessage("usuario.nao.encontrado", null,
                				LocaleContextHolder.getLocale())));
    }
//    "Usuário não encontrado"
    @Override
    @Transactional(readOnly = true)
    public UsuarioRequestDTO buscarPorUsername(String username) {
        return usuarioRepository.findByUsuario(username)
                .map(usuarioMapper::toDto)
                .orElseThrow(() -> new RegraDeNegocioException(
                		mensagens.pegarMensagem("usuario.nao.encontrado")));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioRequestDTO buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .map(usuarioMapper::toDto)
                .orElseThrow(() -> new RegraDeNegocioException(
                		mensagens.pegarMensagem("cpf.nao.encontrado")
                		));
    }

    @Override
    @Transactional
    public void redefinirSenha(UUID uuid, String novaSenha) {
        Usuario entity = encontrarOuLancarErro(uuid);
        entity.setSenha(passwordEncoder.encode(novaSenha));
        // save implícito pelo contexto transacional (@Transactional + entidade gerenciada)
    }

    /** Regras de Negócio — Ciclo de Vida da Conta **/

    @Override
    @Transactional
    public void ativarConta(UUID uuid) {
        Usuario entity = encontrarOuLancarErro(uuid);
        entity.setIsAtivo(true);
    }

    @Override
    @Transactional
    public void desativarConta(UUID uuid) {
        Usuario entity = encontrarOuLancarErro(uuid);
        entity.setIsAtivo(false);
    }

    /**Validação de Existência**/

    @Override
    public void validarEmail(String email) {
        boolean existeEmail = usuarioRepository.existsByEmail(email);
        if (existeEmail) {
            throw new ErroValidacaoException(mensagens.pegarMensagem("email.ja.existe"));
        }
    }

    @Override
    public void validarCpf(String cpf) {
        boolean existeCpf =  usuarioRepository.existsByCpf(cpf);
        if (existeCpf) {
            throw new ErroValidacaoException(mensagens.pegarMensagem("cpf.ja.existe"));
        }
    }

//    @Override
//    public void validarUsuario(String usuario) {
//        boolean existeUsuario = usuarioRepository.existsByUsuario(usuario);
//        if (existeUsuario) {
//            throw new ErroValidacaoException(mensagens.pegarMensagem("usuario.ja.existe"));
//        }
//    }

    /**Métodos auxiliares privados
     * Busca um usuário pelo ID ou lança {@link RegraDeNegocioException}.
     */
    private Usuario encontrarOuLancarErro(@org.springframework.lang.NonNull UUID uuid) {
        return usuarioRepository.findById(uuid)
                .orElseThrow(() -> new RegraDeNegocioException(
                        mensagens.pegarMensagem("usuario.nao.encontrado.com.id" )));
    }	
}
