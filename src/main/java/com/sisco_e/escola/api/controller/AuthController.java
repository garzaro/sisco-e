package com.sisco_e.escola.api.controller;




import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisco_e.escola.api.dto.JwtResponse;
import com.sisco_e.escola.api.dto.LoginRequestDto;
import com.sisco_e.escola.api.dto.RefreshTokenRequest;
import com.sisco_e.escola.api.dto.UsuarioDTO;
import com.sisco_e.escola.model.entity.RefreshToken;
import com.sisco_e.escola.model.repository.RefreshTokenRepository;
import com.sisco_e.escola.service.JwtService;
import com.sisco_e.escola.service.RefreshTokenService;
import com.sisco_e.escola.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**controlador de autenticacao
 * [x] Login (/auth/login)
 * [x] Registro / Cadastro inicial (/auth/register), mantê-lo aqui centraliza a porta de entrada.
 * [] Renovação de Token (/auth/refresh)
 * [] Logout ( qundo houver invalidação de refresh token)
 *
 * /auth/login        -> AuthController (Gera o Token)
 * /auth/register     -> AuthController (Cria a conta e libera acesso)
 * /auth/refresh      -> AuthController (Renova o Access Token)
 * **/

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;

    /**
     * Erros de validação e conflito são tratados pelo GlobalExceptionHandler por exemplo.
     * O Controller apenas recebe o DTO e delega ao Service. Nao deve saber dos detalhes da entidade e servico
     * Cadastra um novo usuário.
     */
    @PostMapping("/join/sign-up")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDto){

        UsuarioDTO criarUsuario = usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criarUsuario);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails);

        return ResponseEntity.ok(
                new JwtResponse(accessToken, refreshToken.getToken(), "Bearer")
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        String requestRefreshToken = request.refreshToken();

        return refreshTokenService.findByToken(request.refreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(refreshToken.getEmail());
                    String accessToken = jwtService.generateToken(userDetails);
                    /**Rotacionar o refresh token aqui se desejar**/
                    refreshTokenRepository.delete(refreshToken);
                    /**cria e salva um novo refreshtoken**/
                    RefreshToken novoRefreshToken = refreshTokenService.createRefreshToken(userDetails);
                    /**Retorna ambos os novos tokens para o cliente**/
                    return ResponseEntity.ok(new JwtResponse(accessToken, novoRefreshToken.getToken(), "Bearer"));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token inexistente!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            refreshTokenService.deleteByEmail(userDetails.getUsername());
        }
        return ResponseEntity.noContent().build();
    }
}
