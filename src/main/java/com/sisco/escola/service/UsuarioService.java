package com.sisco.escola.service;

import com.sisco.escola.api.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Contrato de serviço para operações relacionadas ao {@link com.sisco.escola.model.entity.Usuario}.
 *
 * <p>Segue os princípios de Arquitetura Limpa:
 * <ul>
 *   <li>Retorna apenas {@link UsuarioDTO}, nunca a entidade JPA diretamente.</li>
 *   <li>Separa responsabilidades de CRUD, identidade/segurança e regras de negócio.</li>
 *   <li>Listagens suportam paginação via {@link Pageable}.</li>
 * </ul>
 */
public interface UsuarioService {

    // -------------------------------------------------------------------------
    // CRUD Essencial
    // -------------------------------------------------------------------------

    /**
     * Cadastra um novo usuário no sistema.
     * Valida unicidade de e-mail, CPF e username antes de persistir.
     *
     * @param usuarioDTO dados do usuário a ser criado
     * @return DTO do usuário criado com {@code id} e {@code dataCadastro} preenchidos
     */
    UsuarioDTO cadastrar(UsuarioDTO usuarioDTO);

    /**
     * Atualiza os dados de um usuário existente.
     * Campos de identidade (email, CPF, username) são validados contra duplicidade.
     *
     * @param id         identificador do usuário
     * @param usuarioDTO novos dados do usuário
     * @return DTO atualizado
     */
    UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDTO);

    /**
     * Busca um usuário pelo seu identificador primário.
     *
     * @param id identificador do usuário
     * @return DTO do usuário encontrado
     * @throws com.sisco.escola.exception.RegraDeNegocioException se não encontrado
     */
    UsuarioDTO buscarPorId(Long id);

    /**
     * Exclui permanentemente um usuário do sistema.
     * Prefira {@link #desativarConta(Long)} para soft-delete.
     *
     * @param id identificador do usuário a ser removido
     */
    void deletar(Long id);

    /**
     * Lista todos os usuários de forma paginada.
     *
     * @param pageable parâmetros de paginação e ordenação
     * @return página de DTOs
     */
    Page<UsuarioDTO> listarTodos(Pageable pageable);

    // -------------------------------------------------------------------------
    // Segurança e Identidade  (Spring Security / autenticação)
    // -------------------------------------------------------------------------

    /**
     * Autentica um usuário verificando e-mail e senha (hash).
     * Usado pelo fluxo de login; <strong>não</strong> retorna a senha no DTO.
     *
     * @param email e-mail do usuário
     * @param senha senha em texto plano
     * @return DTO do usuário autenticado
     * @throws com.sisco.escola.exception.ErroAutenticacaoException se as credenciais forem inválidas
     */
    UsuarioDTO autenticar(String email, String senha);

    /**
     * Carrega um usuário pelo e-mail — consumido pelo {@code UserDetailsService} do Spring Security.
     *
     * @param email e-mail do usuário
     * @return DTO do usuário encontrado
     * @throws com.sisco.escola.exception.RegraDeNegocioException se não encontrado
     */
    UsuarioDTO buscarPorEmail(String email);

    /**
     * Carrega um usuário pelo username — consumido pelo {@code UserDetailsService} do Spring Security.
     *
     * @param username nome de usuário
     * @return DTO do usuário encontrado
     * @throws com.sisco.escola.exception.RegraDeNegocioException se não encontrado
     */
    UsuarioDTO buscarPorUsername(String username);

    /**
     * Busca um usuário pelo CPF.
     *
     * @param cpf CPF no formato {@code 000.000.000-00}
     * @return DTO do usuário encontrado
     * @throws com.sisco.escola.exception.RegraDeNegocioException se não encontrado
     */
    UsuarioDTO buscarPorCpf(String cpf);

    /**
     * Redefine a senha de um usuário.
     * A nova senha é validada e armazenada como hash (Argon2).
     *
     * @param id         identificador do usuário
     * @param novaSenha  nova senha em texto plano (será validada e codificada)
     */
    void redefinirSenha(Long id, String newPassword);

    // -------------------------------------------------------------------------
    // Regras de Negócio — Ciclo de Vida da Conta
    // -------------------------------------------------------------------------

    /**
     * Ativa a conta de um usuário (define {@code ativo = true}).
     *
     * @param id identificador do usuário
     */
    void ativarConta(Long id);

    /**
     * Desativa a conta de um usuário (soft-delete / {@code ativo = false}).
     * O registro permanece no banco para fins de auditoria.
     *
     * @param id identificador do usuário
     */
    void desativarConta(Long id);

    // -------------------------------------------------------------------------
    // Validação de Existência  (unicidade de campos de identidade)
    // -------------------------------------------------------------------------

    /**
     * Verifica se o e-mail já está em uso por outro usuário.
     *
     * @param email e-mail a ser verificado
     * @throws com.sisco.escola.exception.ErroValidacaoException se o e-mail já existir
     */
    void validarEmail(String email);

    /**
     * Verifica se o CPF já está em uso por outro usuário.
     *
     * @param cpf CPF a ser verificado
     * @throws com.sisco.escola.exception.ErroValidacaoException se o CPF já existir
     */
    void validarCpf(String cpf);

    /**
     * Verifica se o username já está em uso por outro usuário.
     *
     * @param username username a ser verificado
     * @throws com.sisco.escola.exception.ErroValidacaoException se o username já existir
     */
    void validarUsername(String username);
}
