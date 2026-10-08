package com.sisco_e.escola.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JwtServiceImpl - Testes Unitários")
class JwtServiceImplTest {

    private static final String SECRET_32 = "0123456789abcdef0123456789abcdef"; // 32 bytes
    private static final String SECRET_64 = SECRET_32.repeat(2); // 64 bytes
    private static final long EXPIRATION = 60000;
    private static final long REFRESH_EXPIRATION = 120000;

    private JwtServiceImpl createService(String secret) {
        return new JwtServiceImpl(secret, EXPIRATION, REFRESH_EXPIRATION);
    }

    private UserDetails createUser(String username) {
        return new User(username, "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    // --- 3.1 Construtor ---

    @Test
    @DisplayName("C-01 a C-03: secret null, vazio ou em branco deve lançar IllegalArgumentException")
    void construtor_SecretInvalido_LancaExcecao() {
        assertThatThrownBy(() -> new JwtServiceImpl(null, EXPIRATION, REFRESH_EXPIRATION))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new JwtServiceImpl("", EXPIRATION, REFRESH_EXPIRATION))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new JwtServiceImpl("   ", EXPIRATION, REFRESH_EXPIRATION))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("C-04: secret com menos de 32 bytes deve lançar WeakKeyException")
    void construtor_SecretCurto_LancaExcecao() {
        String curto = "1234567890123456789012345678901"; // 31 bytes
        assertThatThrownBy(() -> createService(curto))
                .isInstanceOf(WeakKeyException.class);
    }

    @Test
    @DisplayName("C-05: secret com 32 bytes deve construir e usar HS256")
    void construtor_32Bytes_ConstruiEUSAHS256() {
        JwtServiceImpl service = createService(SECRET_32);
        String token = service.generateToken(createUser("user@test.com"));
        
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_32.getBytes(UTF_8)))
                .build()
                .parseSignedClaims(token);
        
        assertThat(jws.getHeader().getAlgorithm()).isEqualTo("HS256");
    }

    @Test
    @DisplayName("C-06: 64 bytes deve usar HS512")
    void construtor_64Bytes_UsaHS512() {
        JwtServiceImpl service = createService(SECRET_64);
        String token = service.generateToken(createUser("user@test.com"));
        
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_64.getBytes(UTF_8)))
                .build()
                .parseSignedClaims(token);
        
        assertThat(jws.getHeader().getAlgorithm()).isEqualTo("HS512");
    }

    @Test
    @DisplayName("C-08: tokens de instâncias com mesmo secret devem ser aceitos mutuamente")
    void construtor_MesmoSecret_AceitaToken() {
        JwtServiceImpl service1 = createService(SECRET_32);
        JwtServiceImpl service2 = createService(SECRET_32);
        
        UserDetails user = createUser("user@test.com");
        String token = service1.generateToken(user);
        
        assertThat(service2.isTokenValid(token, user)).isTrue();
    }

    @Test
    @DisplayName("C-09: tokens de instâncias com secrets diferentes devem ser rejeitados")
    void construtor_SecretsDiferentes_RejeitaToken() {
        JwtServiceImpl service1 = createService(SECRET_32);
        JwtServiceImpl service2 = createService(SECRET_32.replace('0', '1'));
        
        UserDetails user = createUser("user@test.com");
        String token = service1.generateToken(user);
        
        assertThat(service2.isTokenValid(token, user)).isFalse();
    }

    // --- 3.2 generateToken ---

    @Test
    @DisplayName("G-01: sub deve ser o username")
    void generateToken_UserDetailsComum_SubCorreto() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("alvo@test.com");
        String token = service.generateToken(user);
        
        assertThat(service.obterUserDetailsLogin(token)).isEqualTo("alvo@test.com");
    }

    @Test
    @DisplayName("G-04: não deve conter PII (cpf, nome completo)")
    void generateToken_SemPII() {
        JwtServiceImpl service = createService(SECRET_32);
        String token = service.generateToken(createUser("user@test.com"));
        
        Claims claims = service.obterClaims(token);
        assertThat(claims).doesNotContainKeys("cpf", "nome completo");
        // O código real inclui a claim "horaExpiração", validar que está presente
        assertThat(claims).containsKey("horaExpiração");
    }

    @Test
    @DisplayName("G-07: round-trip generateToken -> isTokenValid")
    void generateToken_RoundTrip_Valido() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("user@test.com");
        String token = service.generateToken(user);
        
        assertThat(service.isTokenValid(token, user)).isTrue();
    }

    @Test
    @DisplayName("G-09: token deve ter 3 partes")
    void generateToken_TresPartes() {
        JwtServiceImpl service = createService(SECRET_32);
        String token = service.generateToken(createUser("user@test.com"));
        
        assertThat(token.split("\\.")).hasSize(3);
    }

    // --- 3.3 generateRefreshToken ---

    @Test
    @DisplayName("R-01: generateRefreshToken deve retornar token não nulo")
    void generateRefreshToken_RetornaToken() {
        JwtServiceImpl service = createService(SECRET_32);
        String result = service.generateRefreshToken(createUser("user@test.com"));
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("R-02: refresh token deve ter subject correto")
    void generateRefreshToken_SubjectCorreto() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("refresh@test.com");
        String token = service.generateRefreshToken(user);

        assertThat(service.obterUserDetailsLogin(token)).isEqualTo("refresh@test.com");
    }

    @Test
    @DisplayName("R-03: refresh token deve ter expiração maior que access token")
    void generateRefreshToken_ExpiracaoMaior() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("user@test.com");

        String accessToken = service.generateToken(user);
        String refreshToken = service.generateRefreshToken(user);

        Claims accessClaims = service.obterClaims(accessToken);
        Claims refreshClaims = service.obterClaims(refreshToken);

        assertThat(refreshClaims.getExpiration().getTime())
                .isGreaterThan(accessClaims.getExpiration().getTime());
    }

    @Test
    @DisplayName("R-04: refresh token deve ter 3 partes")
    void generateRefreshToken_TresPartes() {
        JwtServiceImpl service = createService(SECRET_32);
        String token = service.generateRefreshToken(createUser("user@test.com"));

        assertThat(token.split("\\.")).hasSize(3);
    }

    @Test
    @DisplayName("R-05: refresh token não deve conter horaExpiração claim")
    void generateRefreshToken_SemHoraExpiracao() {
        JwtServiceImpl service = createService(SECRET_32);
        String token = service.generateRefreshToken(createUser("user@test.com"));

        Claims claims = service.obterClaims(token);
        assertThat(claims).doesNotContainKey("horaExpiração");
    }

    // --- 3.4 obterClaims ---

    @Test
    @DisplayName("O-01: token válido retorna claims corretos")
    void obterClaims_TokenValido_RetornaClaims() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("user@test.com");
        String token = service.generateToken(user);
        
        Claims claims = service.obterClaims(token);
        assertThat(claims.getSubject()).isEqualTo("user@test.com");
        assertThat(claims.getIssuedAt()).isBeforeOrEqualTo(new Date());
        assertThat(claims.getExpiration()).isAfter(new Date());
    }

    @ParameterizedTest
    @MethodSource("invalidTokens")
    @DisplayName("O-02 a O-08, O-16: tokens malformados ou inválidos devem lançar exceção")
    void obterClaims_TokensInvalidos_LancaExcecao(String invalidToken) {
        JwtServiceImpl service = createService(SECRET_32);
        assertThatThrownBy(() -> service.obterClaims(invalidToken))
                .isInstanceOfAny(JwtException.class, IllegalArgumentException.class);
    }

    static Stream<String> invalidTokens() {
        return Stream.of(
            null,
            "",
            "   ",
            "abc",
            "a.b",
            "a.b.c.d",
            "@@@.@@@.@@@",
            "Bearer valid.token.here"
        );
    }

    @Test
    @DisplayName("O-10: token assinado com outra chave lança SignatureException")
    void obterClaims_ChaveErrada_LancaSignatureException() {
        JwtServiceImpl service = createService(SECRET_32);
        
        String tokenOutraChave = Jwts.builder()
                .subject("user")
                .signWith(Keys.hmacShaKeyFor(SECRET_64.getBytes(UTF_8)))
                .compact();
        
        assertThatThrownBy(() -> service.obterClaims(tokenOutraChave))
                .isInstanceOf(SignatureException.class);
    }

    @Test
    @DisplayName("O-14: token expirado lança ExpiredJwtException")
    void obterClaims_Expirado_LancaExpiredJwtException() {
        JwtServiceImpl service = createService(SECRET_32);
        
        String tokenExpirado = Jwts.builder()
                .subject("user")
                .expiration(Date.from(Instant.now().minusSeconds(3600)))
                .signWith(Keys.hmacShaKeyFor(SECRET_32.getBytes(UTF_8)))
                .compact();
        
        assertThatThrownBy(() -> service.obterClaims(tokenExpirado))
                .isInstanceOf(ExpiredJwtException.class);
    }

    // --- 3.5 isTokenValid ---

    @Test
    @DisplayName("V-01: token válido, sub igual -> true")
    void isTokenValid_Valido_True() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("user@test.com");
        String token = service.generateToken(user);
        
        assertThat(service.isTokenValid(token, user)).isTrue();
    }

    @Test
    @DisplayName("V-02: token válido, username diferente -> false")
    void isTokenValid_UsernameDiferente_False() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user1 = createUser("user1@test.com");
        UserDetails user2 = createUser("user2@test.com");
        String token = service.generateToken(user1);
        
        assertThat(service.isTokenValid(token, user2)).isFalse();
    }

    @Test
    @DisplayName("V-04: token expirado -> false")
    void isTokenValid_Expirado_False() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("user@test.com");
        
        String tokenExpirado = Jwts.builder()
                .subject(user.getUsername())
                .expiration(Date.from(Instant.now().minusSeconds(3600)))
                .signWith(Keys.hmacShaKeyFor(SECRET_32.getBytes(UTF_8)))
                .compact();
        
        assertThat(service.isTokenValid(tokenExpirado, user)).isFalse();
    }

    @Test
    @DisplayName("V-06: token sem sub -> false")
    void isTokenValid_SemSub_False() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("user@test.com");
        
        String tokenSemSub = Jwts.builder()
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(Keys.hmacShaKeyFor(SECRET_32.getBytes(UTF_8)))
                .compact();
        
        assertThat(service.isTokenValid(tokenSemSub, user)).isFalse();
    }

    @Test
    @DisplayName("V-07: null, vazio -> false")
    void isTokenValid_NullVazio_False() {
        JwtServiceImpl service = createService(SECRET_32);
        UserDetails user = createUser("user@test.com");
        
        assertThat(service.isTokenValid(null, user)).isFalse();
        assertThat(service.isTokenValid("", user)).isFalse();
        assertThat(service.isTokenValid("   ", user)).isFalse();
    }

    @Test
    @DisplayName("V-09: userDetails null -> false")
    void isTokenValid_UserDetailsNull_False() {
        JwtServiceImpl service = createService(SECRET_32);
        String token = service.generateToken(createUser("user@test.com"));
        
        assertThat(service.isTokenValid(token, null)).isFalse();
    }
}
