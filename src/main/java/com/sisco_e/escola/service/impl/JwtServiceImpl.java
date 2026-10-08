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
            return obterClaims(token).getExpiration() != null;
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
//
//package com.sisco_e.escola.service.impl;
//
//import com.sisco_e.escola.model.entity.Usuario;
//import com.sisco_e.escola.service.JwtService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.time.Instant;
//import java.time.ZoneOffset;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
///**
// * Esta implementação é responsável por assinar o token usando uma chave secreta,
// * extrair o nome de usuário (subject) e validar se o token expirou ou é inválido.
// * **/
//
//@Service
//public class JwtServiceImpl implements JwtService {
//
////    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
//
//    private final SecretKey signingKey;
//    private final long expirationMs;
//    private final long expirationRefeshToken;
//
//    public JwtServiceImpl(
//            @Value("${jwt.security.jwtExpirationMs}")
//            long expirationMs,
//
//            @Value("${jwt.security.jwtSecretKey}")
//            String secret,
//
//            @Value("${jwt.security.refreshExpiration}")
//            long expirationRefeshToken
//    ){
//        if (secret == null || secret.isBlank()) {
//            throw new IllegalArgumentException("JWT Secret Key não configurada. Verifique as variáveis de ambiente!");
//        }
//        this.expirationMs = expirationMs;
//        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//        this.expirationRefeshToken = expirationRefeshToken;
//    }
//
//    @Override
//    public String generateToken(Usuario usuario) {
//        Instant agora = Instant.now();
//        Instant expiracao = agora.plusMillis(expirationMs);
//
//        String horaFormatada = DateTimeFormatter
//                .ofPattern("HH:mm:ss")
//                .withZone(ZoneOffset.UTC)
//                .format(expiracao);
//
//        return Jwts.builder()
//                .issuedAt(Date.from(agora))
//                .expiration(Date.from(expiracao))
//                .subject(usuario.getEmail())
//                .claim("uuid", usuario.getUuid())
//                .claim("nome completo", usuario.getNomeCompleto())
//                .claim("cpf", usuario.getCpf())
//                .claim("horaExpiração", horaFormatada)
//                .signWith(signingKey)
//                .compact();
//    }
//
//    @Override
//    public String generateRefreshToken(UserDetails userDetails) {
//        return null; // buildToken(new HashMap<>(), userDetails, expirationRefeshToken);
//    }
//
//    /**EXTRAIR CLAIMS / VALIDAR **/
//    @Override
//    public Claims obterClaims(String token) throws JwtException, IllegalArgumentException {
//        return Jwts
//                .parser()
//                .verifyWith(signingKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    /**Date dataExpiracao = obterClaim(token, Claims::getExpiration);**/
//    @Override
//    public boolean isTokenValid(String token) {
//        try {
//            return obterClaims(token).getExpiration() != null;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//// istokenValid de forma mais automatica
////    try {
////        // O parser já valida a assinatura, estrutura E a data de expiração automaticamente.
////        // Se qualquer uma dessas falhar, uma JwtException será lançada.
////        obterClaims(token);
////
////        // Se chegou até aqui, o token é válido e não está expirado.
////        return true;
////
////    } catch (JwtException | IllegalArgumentException e) {
////        // Qualquer erro (expirado, assinatura inválida, malformado, etc.) cai aqui.
////        return false;
////    }
//
//
//    @Override
//    public String obterUserDetailsLogin(String token) {
//        Claims claims = obterClaims(token);
//        return claims.getSubject();
//    }
//}
