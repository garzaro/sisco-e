package com.sisco.escola.service.impl;

import com.sisco.escola.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementação do serviço de processamento e gerenciamento de tokens JWT usando a biblioteca JJWT 0.12.x.
 * 
 * Esta classe utiliza assinaturas HMAC baseadas em SHA-256 e garante criptografia/validação de tokens stateless.
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.security.jwt.secret}")
    private String secret;

    @Value("${app.security.jwt.expiration-ms}")
    private long expiracaoMs;

    /**
     * Obtém a chave criptográfica HMAC-SHA a partir da String de segredo injetada.
     * Garante que o segredo tenha o tamanho mínimo adequado para HMAC-SHA256 (256 bits).
     */
    private SecretKey obterChaveAssinatura() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String gerarToken(String username) {
        return gerarToken(username, new HashMap<>());
    }

    @Override
    public String gerarToken(String username, Map<String, Object> claimsExtra) {
        Date dataAtual = new Date();
        Date dataExpiracao = new Date(dataAtual.getTime() + expiracaoMs);

        // API Moderna do JJWT 0.12.x: usa verifyWith e signWith de forma tipada
        return Jwts.builder()
                .claims(claimsExtra)
                .subject(username)
                .issuedAt(dataAtual)
                .expiration(dataExpiracao)
                .signWith(obterChaveAssinatura())
                .compact();
    }

    @Override
    public String obterUsername(String token) {
        return extrairTodasClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValido(String token) {
        try {
            Claims claims = extrairTodasClaims(token);
            // Verifica se o token expirou
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // Lida silenciosamente com tokens malformados, expirados ou com assinaturas corrompidas no filtro
            return false;
        }
    }

    /**
     * Extrai todas as claims de um token JWT, validando sua assinatura no processo.
     *
     * @param token String contendo o JWT
     * @return objeto {@link Claims} contendo o payload descriptografado
     * @throws JwtException se a assinatura for inválida ou o token estiver corrompido
     */
    private Claims extrairTodasClaims(String token) {
        return Jwts.parser()
                .verifyWith(obterChaveAssinatura())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
