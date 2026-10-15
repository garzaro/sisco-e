package com.sisco_e.escola.service.impl;

import com.sisco_e.escola.model.entity.Usuario;
import com.sisco_e.escola.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Esta implementação é responsável por assinar o token usando uma chave secreta,
 * extrair o nome de usuário (subject) e validar se o token expirou ou é inválido.
 *
 * TODO-list
 * [] Verificar o tempo de access token e refreshtoken, ste deve ser maior - 7 dias.
 * **/

@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey signingKey;
    private final long expirationMs;
    private final long expirationRefeshToken;

    public JwtServiceImpl(
            @Value("${app.security.jwt.jwtSecretKey}") String secret,
            @Value("${app.security.jwt.jwtExpirationMs}") long expirationMs,
            @Value("${app.security.jwt.refresh-expiration}") long expirationRefeshToken
    ){
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT Secret Key não configurada. Verifique as variáveis de ambiente!");
        }
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
        this.expirationRefeshToken = expirationRefeshToken;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Instant agora =  Instant.now();
        Instant expiracao = agora.plusMillis(expirationMs);

        String horaFormatada = DateTimeFormatter
                .ofPattern("HH:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(expiracao);

        logger.debug("Subject (email) gravado no token: {}", userDetails.getUsername());

        return Jwts.builder()
                .issuedAt(Date.from(agora))
                .expiration(Date.from(expiracao))
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .claim("horaExpiração", horaFormatada)
                .signWith(signingKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        Instant agora = Instant.now();
        Instant expiracao = agora.plusMillis(expirationRefeshToken);

        return Jwts.builder()
                .issuedAt(Date.from(agora))
                .expiration(Date.from(expiracao))
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .signWith(signingKey)
                .compact();
    }
    /**extração**/
    @Override
    public Claims obterClaims(String token) throws JwtException, IllegalArgumentException {
        return Jwts
                .parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            if (userDetails == null) return false;
            Claims claims = obterClaims(token);
            String email = claims.getSubject(); // email gravado na criaçãõ do token
            return email != null
                    && email.equals(userDetails.getUsername()) //email na base de dados
                    && claims.getExpiration() != null;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String obterUserDetailsLogin(String token) {
        Claims claims = obterClaims(token);
        return claims.getSubject();
    }
}

