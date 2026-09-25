package com.sisco_e.escola.service;

import com.sisco_e.escola.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Contrato do serviço JWT da aplicação.
 * Responsabilidades:
 * Geração de tokens assinados com HMAC-SHA
 * Extração e validação de claims
 * Verificação de assinatura, ISSUER e expiração
 * O {@link com.cleber.financas.security.JwtAuthenticationFilter} consome este serviço
 * para autenticar cada requisição.
 */
public interface JwtService {

    /**
     * Gera um token JWT com apenas o {@code username} do {@link UserDetails} como subject.
     * Usado internamente pelo filtro de autenticação e pelo endpoint de login.
     */
    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    /**
     * Retorna {@code true} se o token for válido, não expirado e pertencer ao
     * {@code userDetails} informado.
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Retorna o login (e-mail / username) armazenado no subject do token.
     *
     * @throws RuntimeException se o token for inválido ou expirado
     */
    String extractUsername(String token);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    Claims extractAllClaims(String token);

    Key getSigningKey();

    /**
     * Gera um token JWT enriquecido para um {@link Usuario}, incluindo claims extras:
     * {@code id}, {@code cpf}, {@code nome_usuario} e {@code nome}.
     *Gera um token com claims extras adicionais ao payload.
     * */
    String generateTokenWithAllClaims(Map<String, Object> extraClaims, UserDetails userDetails);
}
