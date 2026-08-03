package com.sisco_e.escola.service.impl;

import com.sisco_e.escola.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    /**
     * Identifica o emissor — validado no parse para evitar tokens de outros
     * sistemas.
     */
    private static final String ISSUER = "financas-api";

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtServiceImpl(
            @Value("${JWT_SECRET}") String secret,
            @Value("${JWT_EXPIRATION_MS}") long expirationMs) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT_SECRET não configurado");
        }

        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    @Override
    public String extrairUsernameToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String gerarToken(UserDetails userDetails) {
        return gerarTokenComClaims(new HashMap<>(), userDetails);
    }

    @Override
    public String gerarTokenComClaims(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    @Override
    public boolean isTokenValido(String token, UserDetails userDetails) {
        try {
            final String username = extrairUsernameToken(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (ExpiredJwtException ex) {
            logger.warn("Tentativa de uso de token expirado para o usuário: {}", ex.getClaims().getSubject());
            return false;
        }
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
