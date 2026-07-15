package com.sisco.escola.service.impl;

import com.sisco.escola.service.JwtServiceProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementação do serviço de processamento e gerenciamento de tokens JWT usando a biblioteca JJWT 0.12.x.
 *
 * Esta classe utiliza assinaturas HMAC baseadas em SHA-256 e garante criptografia/validação de tokens stateless.
 */
@Service
public class JwtServiceProviderImpl implements JwtServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceProviderImpl.class);

    private final SecretKey signingKey;
    private final long jwtExpirationMs;

    public JwtServiceProviderImpl(
            @Value("${JWT_SECRET}") String secret,
            @Value("${JWT_EXPIRATION_MS}") long expirationMs
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = expirationMs;
    }

    /**extração**/
    @Override
    public String extrairUsernameToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Evita duplicação de parsing. Sem esse metodo generico, teria que chamar extractAllClaims(token)
     * (que faz o parse/verificação da assinatura — operação cara) toda vez que quisesse ler um campo diferente:
     **/
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extrairTodasClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String gerarToken(UserDetails userDetails) {
        return gerarTokenComClaims(new HashMap<>(), userDetails);
    }

    public String gerarTokenComClaims(Map<String, Object> extraClaims, UserDetails userDetails){
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + jwtExpirationMs);
        /**API do JJWT 0.12.x: usa verifyWith e signWith de forma tipada**/
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(signingKey)
                .compact();
    }

    @Override
    public boolean isTokenValido(String token, UserDetails userDetails) {
        try {
            /**O email no token deve bater com o carregado pelo banco e nao expirado**/
            final String username = extrairUsernameToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpirado(token));
        }catch (ExpiredJwtException ex){
            logger.warn("Token JWT expirado: {}" + ex.getMessage());
            return false;
        }
    }

    private boolean isTokenExpirado(String token) {
        return extrairExpiracao(token).before(new Date());
    }

    private Date extrairExpiracao(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrai todas as claims de um token JWT, validando sua assinatura no processo.
     * @param token String contendo o JWT
     * @return objeto {@link Claims} contendo o payload descriptografado
     * @throws JwtException se a assinatura for inválida ou o token estiver corrompido
     */
    private Claims extrairTodasClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}








