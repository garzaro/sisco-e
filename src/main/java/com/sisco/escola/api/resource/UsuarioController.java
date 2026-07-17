package com.sisco.escola.api.resource;

import com.sisco.escola.api.dto.*;
import com.sisco.escola.service.JwtServiceProvider;
import com.sisco.escola.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller de autenticação (login e registro de usuário).
 *
 * Endpoints:
 * - POST /api/auth/sign-in/      → Login com email/senha, retorna token JWT
 * - POST /api/user/sign-up/      → Registro de novo usuário, retorna 201 Created
 *
 * Injeção via construtor + @RequiredArgsConstructor (Lombok).
 * Exceções de negócio/validação são tratadas pelo GlobalExceptionHandler centralizado.
 *
 * Segurança:
 * - NUNCA loga senhas (nem mascaradas).
 * - Log de tentativas de login falhas apenas com email (WARN level).
 * - Log de registro bem-sucedido com INFO level (sem senha).
 * - Mensagens de erro genéricas para credenciais inválidas (evita user enumeration).
 */

//AuthController
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UsuarioController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtServiceProvider jwtServiceProvider;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    /**
     * POST /api/auth/sign-in/
     *
     * Autentica o usuário usando email e senha.
     * O authenticationManager delega ao DaoAuthenticationProvider que:
     * 1. Carrega UserDetails pelo email via UserDetailsService
     * 2. Compara a senha fornecida com a hash armazenada (Argon2)
     *
     * Se as credenciais forem inválidas, BadCredentialsException é lançada
     * e tratada pelo GlobalExceptionHandler → 401 Unauthorized.
     *
     * Se autenticado com sucesso:
     * 1. Carrega UserDetails do usuário autenticado
     * 2. Gera token JWT via JwtServiceProvider
     * 3. Retorna 200 com AuthResponseDTO (token + tokenType)
     *
     * @param loginRequestDTO DTO contendo email e senha (validados via @Valid)
     * @return 200 OK com AuthResponseDTO
     * @throws BadCredentialsException (tratada por GlobalExceptionHandler → 401)
     */
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> autenticar(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        /**Autentica: throws BadCredentialsException se inválido, não captura aqui**/
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(),
                        loginRequestDTO.senha()
                )
        );
        /**se chegou ate aqui, as credenciai sao validas
         * Carrega Userdetails pra gerar token
         * **/
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.email());
        String token = jwtServiceProvider.gerarToken(userDetails);
//        log.info("Usuário autenticado com sucesso: {}", loginRequestDTO.email());
        return ResponseEntity.ok(new AuthResponseDTO(token, "Bearer"));
    }

    /**
     * POST /api/user/sign-up/
     *
     * Registra um novo usuário no sistema.
     *
     * Fluxo:
     * 1. Bean Validation valida campos (@Valid no DTO)
     * 2. Verifica se email já existe (UsuarioService.validarEmail lança ErroValidacaoException)
     * 3. Codifica senha com Argon2 via PasswordEncoder
     * 4. Persiste usuário via UsuarioService
     * 5. Retorna 201 Created (SEM token — registro e login são separados)
     *
     * Registro e login são endpoints diferentes por design:
     * - Deixa espaço para validação/confirmação de email no futuro
     * - Força o cliente a fazer login após criar a conta
     * - Segue padrão REST: POST /sign-up/ cria recurso, POST /sign-in/ autentica
     *
     * @param registerRequest DTO contendo dados de registro (validados via @Valid)
     * @return 201 Created com UsuarioDTO (sem senha)
     * @throws ErroValidacaoException se email/CPF/username já existem (tratada por GlobalExceptionHandler → 409)
     */
    @PostMapping("/sign-up/")
    public ResponseEntity<UsuarioRequestDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        /**cria dto para persistencia**/
        UsuarioRequestDTO criarUsuario = UsuarioRequestDTO.builder()
                .nome(usuarioRequestDTO.getNome())
                .cpf(usuarioRequestDTO.getCpf())
                .usuario(usuarioRequestDTO.getUsuario())
                .email(usuarioRequestDTO.getEmail())
                .senha(passwordEncoder.encode(usuarioRequestDTO.getSenha()))
                .isAtivo(true)
                .build();
        /**persiste e valida unicidade (ErroValidacaoException é lançada se
         * email/CPF/username já existem)
         * **/
        UsuarioRequestDTO usuarioCriado = usuarioService.cadastrarUsuario(criarUsuario);
//        log.info("Novo usuário registrado com sucesso: {}", registerRequest.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    /**
     * Atualiza os dados de um usuário existente.
     * Erros de validação e conflito são tratados pelo GlobalExceptionHandler (422 / 409).
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRequestDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioRequestDTO atualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Busca um usuário pelo seu ID.
     * Recurso não encontrado é tratado pelo GlobalExceptionHandler (422).
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRequestDTO> buscarPorId(@PathVariable UUID id) {
        UsuarioRequestDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Desativa a conta de um usuário (soft-delete).
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        usuarioService.desativarConta(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Reativa a conta de um usuário.
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable UUID id) {
        usuarioService.ativarConta(id);
        return ResponseEntity.noContent().build();
    }

//    public static UsuarioRequestDTO criarUsuario(UsuarioRequestDTO registerRequest, PasswordEncoder passwordEncoder){
//        return UsuarioRequestDTO.builder()
//                .nome(registerRequest.getNome())
//                .cpf(registerRequest.getCpf())
//                .usuario(registerRequest.getUsuario())
//                .email(registerRequest.getEmail())
//                .senha(passwordEncoder.encode(registerRequest.getSenha())) // Codifica com Argon2
//                .ativo(true)
//                .build();
//
//    }
}
