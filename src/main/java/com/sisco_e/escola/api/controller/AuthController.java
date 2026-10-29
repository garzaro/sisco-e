package com.sisco_e.escola.api.controller;




import com.sisco_e.escola.api.dto.request.LoginRequestDto;
import com.sisco_e.escola.api.dto.request.RefreshTokenRequest;
import com.sisco_e.escola.api.dto.request.UsuarioRequestDTO;
import com.sisco_e.escola.api.dto.response.ApiResponse;
import com.sisco_e.escola.api.dto.response.LoginResponseDTO;
import com.sisco_e.escola.api.dto.response.UsuarioResponseDTO;
import com.sisco_e.escola.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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

import com.sisco_e.escola.model.entity.RefreshToken;
import com.sisco_e.escola.model.repository.RefreshTokenRepository;
import com.sisco_e.escola.service.JwtService;
import com.sisco_e.escola.service.RefreshTokenService;
import com.sisco_e.escola.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**controlador de autenticacao
 * [] Registro / Cadastro inicial (/auth/register), mantê-lo aqui centraliza a porta de entrada.
 * [x] Login (/auth/login)
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
    
    /**
     * Erros de validação e conflito são tratados pelo GlobalExceptionHandler por exemplo.
     * O Controller apenas recebe o DTO e delega ao Service. Nao deve saber dos detalhes da entidade e servico
     * Cadastra um novo usuário.
     */
    @PostMapping("/join/sign-up")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> cadastrarUsuario(
            @RequestBody @Valid UsuarioRequestDTO usuarioDto){

        UsuarioResponseDTO usuarioCriado = usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuário criado com sucesso!", usuarioCriado));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponseDTO> login(
        @RequestBody @Valid LoginRequestDto request) {
        //Autentica o usuário com base no email e senha fornecidos
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        // Obtém os detalhes do usuário autenticado
        Object principal = authentication.getPrincipal();
        // Verifica se o principal é uma instância de UserDetails
        if (!(principal instanceof UserDetails userDetails)) {
            throw new IllegalStateException("A autenticação falhou");
        }
        // Gera o access token e o refresh token para o usuário autenticado
        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails);
        // Cria o DTO de resposta com os tokens e o email do usuário
        LoginResponseDTO response = LoginResponseDTO.builder()
                .email(userDetails.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();

        return ResponseEntity.ok(response);        
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        String requestRefreshToken = request.refreshToken();

        /**Validação criptográfica do JWT (assinatura + expiração) antes de qualquer
         * consulta ao Redis: um token malformado, expirado ou forjado nunca chega lá.**/
        Claims claims;
        try {
            claims = jwtService.obterClaims(requestRefreshToken);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new InvalidRefreshTokenException("Refresh token inválido ou expirado!");
        }

        /**Fail-closed: só refresh token é aceito aqui — um access token no corpo é rejeitado.**/
        if (!JwtService.TOKEN_TYPE_REFRESH.equals(claims.get(JwtService.CLAIM_TOKEN_TYPE, String.class))) {
            throw new InvalidRefreshTokenException("Token apresentado não é um refresh token!");
        }

        return refreshTokenService.findByToken(requestRefreshToken)
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
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token inexistente!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            refreshTokenService.deleteByEmail(userDetails.getUsername());
        }
        return ResponseEntity.noContent().build();
    }
}
