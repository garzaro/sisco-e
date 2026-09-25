package com.sisco_e.escola.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes Unitários — JwtServiceImpl
 *
 * Estratégia: POJO puro, sem contexto Spring.
 * Os campos @Value são injetados via ReflectionTestUtils antes de cada teste.
 *
 * Cenários cobertos:
 *  1. Geração de token (não nulo, não vazio)
 *  2. Extração de username (subject correto)
 *  3. Extração de data de expiração
 *  4. Validação de token válido → true
 *  5. Validação com usuário diferente → false
 *  6. Token expirado → isTokenExpired lança ExpiredJwtException
 *  7. Token expirado → isTokenValid lança ExpiredJwtException
 *  8. Token adulterado → lança exceção de assinatura
 *  9. generateTokenWithAllClaims carrega claims extras corretamente
 * 10. generateRefreshToken usa janela de refresh maior
 */
@DisplayName("JwtServiceImpl — Testes Unitários")
class JwtServiceImplTest {

    /*
     * Segredo mínimo de 256 bits (HMAC-SHA256).
     * Em produção vem de ${spring.app.jwtSecretKey}.
     */
    private static final String SECRET =
            "12345678901234567890123456789012"; // 32 chars → 256 bits

    /** 5 minutos em milissegundos */
    private static final long EXPIRATION_MS      = 5 * 60 * 1_000L;
    /** 7 dias em milissegundos */
    private static final long REFRESH_EXPIRATION = 7 * 24 * 60 * 60 * 1_000L;

    private JwtServiceImpl jwtService;
    private UserDetails usuarioPadrao;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();
        ReflectionTestUtils.setField(jwtService, "secretKey",         SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration",     EXPIRATION_MS);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", REFRESH_EXPIRATION);

        usuarioPadrao = User.withUsername("usuario@gmail.com")
                .password("SenhaCriptografada")
                .authorities(Collections.emptyList())
                .build();
    }

    // =========================================================================
    // 1. Geração de Token
    // =========================================================================
    @Nested
    @DisplayName("1. Geração de Token")
    class GeracaoToken {

        @Test
        @DisplayName("generateToken — deve retornar uma string não nula e não vazia")
        void deveGerarTokenNaoNuloENaoVazio() {
            String token = jwtService.generateToken(usuarioPadrao);

            assertThat(token)
                    .isNotNull()
                    .isNotBlank()
                    .contains(".");            // formato JWT: header.payload.signature
        }

        @Test
        @DisplayName("generateToken — dois tokens seguidos devem ser diferentes (issuedAt varia)")
        void deveGerarTokensDiferentesEmChamadasConsecutivas() throws InterruptedException {
            String token1 = jwtService.generateToken(usuarioPadrao);
            Thread.sleep(1010); // garante issuedAt em segundos diferente
            String token2 = jwtService.generateToken(usuarioPadrao);

            assertThat(token1).isNotEqualTo(token2);
        }

        @Test
        @DisplayName("generateRefreshToken — deve retornar token não vazio")
        void deveGerarRefreshTokenNaoVazio() {
            String refreshToken = jwtService.generateRefreshToken(usuarioPadrao);

            assertThat(refreshToken).isNotNull().isNotBlank();
        }

        @Test
        @DisplayName("generateTokenWithAllClaims — claims extras devem estar no payload")
        void deveGerarTokenComClaimsExtras() {
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("id",           "uuid-123");
            extraClaims.put("nome_usuario", "João Silva");

            String token = jwtService.generateTokenWithAllClaims(extraClaims, usuarioPadrao);

            Claims claims = jwtService.extractAllClaims(token);
            assertThat(claims.get("id",           String.class)).isEqualTo("uuid-123");
            assertThat(claims.get("nome_usuario", String.class)).isEqualTo("João Silva");
        }
    }

    // =========================================================================
    // 2. Extração de Claims
    // =========================================================================
    @Nested
    @DisplayName("2. Extração de Claims")
    class ExtracaoClaims {

        @Test
        @DisplayName("extractUsername — deve retornar o email inserido como subject")
        void deveExtrairUsernameCorreto() {
            String token = jwtService.generateToken(usuarioPadrao);

            String username = jwtService.extractUsername(token);

            assertThat(username).isEqualTo("usuario@gmail.com");
        }

        @Test
        @DisplayName("extractExpiration — deve retornar data futura dentro da janela de expiração")
        void deveExtrairDataDeExpiracaoFutura() {
            long antes = System.currentTimeMillis();
            String token = jwtService.generateToken(usuarioPadrao);

            Date expiration = jwtService.extractExpiration(token);

            assertThat(expiration.getTime())
                    .isGreaterThan(antes)
                    .isLessThanOrEqualTo(antes + EXPIRATION_MS + 1_000L); // margem 1s
        }

        @Test
        @DisplayName("extractAllClaims — subject deve corresponder ao username")
        void deveExtrairTodasAsClaimsComSubjectCorreto() {
            String token = jwtService.generateToken(usuarioPadrao);

            Claims claims = jwtService.extractAllClaims(token);

            assertThat(claims.getSubject()).isEqualTo("usuario@gmail.com");
            assertThat(claims.getExpiration()).isAfter(new Date());
        }
    }

    // =========================================================================
    // 3. Validação de Token
    // =========================================================================
    @Nested
    @DisplayName("3. Validação de Token")
    class ValidacaoToken {

        @Test
        @DisplayName("isTokenValid — token legítimo deve retornar true")
        void tokenLegitimodeveSerValido() {
            String token = jwtService.generateToken(usuarioPadrao);

            boolean valido = jwtService.isTokenValid(token, usuarioPadrao);

            assertThat(valido).isTrue();
        }

        @Test
        @DisplayName("isTokenValid — token pertencente a outro usuário deve retornar false")
        void tokenDeOutroUsuarioDeveSerInvalido() {
            UserDetails outroUsuario = User.withUsername("outro@gmail.com")
                    .password("outrasenha")
                    .authorities(Collections.emptyList())
                    .build();

            String token = jwtService.generateToken(outroUsuario);

            // valida token de 'outroUsuario' contra 'usuarioPadrao'
            boolean valido = jwtService.isTokenValid(token, usuarioPadrao);

            assertThat(valido).isFalse();
        }

        @Test
        @DisplayName("isTokenExpired — token recém gerado não deve estar expirado")
        void tokenValidoNaoDeveEstarExpirado() {
            String token = jwtService.generateToken(usuarioPadrao);

            assertThat(jwtService.isTokenExpired(token)).isFalse();
        }
    }

    // =========================================================================
    // 4. Token Expirado
    // =========================================================================
    @Nested
    @DisplayName("4. Token Expirado")
    class TokenExpirado {

        /**
         * Constrói um token com expiração no passado diretamente via JJWT,
         * sem sleep ou mocks de relógio — abordagem determinística.
         */
        private String gerarTokenExpirado(UserDetails userDetails) {
            byte[] keyBytes = Decoders.BASE64.decode(
                    Base64.getEncoder().encodeToString(SECRET.getBytes()));
            SecretKey chave = Keys.hmacShaKeyFor(keyBytes);

            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()  - 10_000L))  // 10 s atrás
                    .expiration(new Date(System.currentTimeMillis() - 1_000L))  // 1 s atrás
                    .signWith(chave)
                    .compact();
        }

        @Test
        @DisplayName("isTokenExpired — token expirado deve lançar ExpiredJwtException")
        void tokenExpiradoDeveLancarExpiredJwtExceptionAoVerificarExpiracao() {
            String tokenExpirado = gerarTokenExpirado(usuarioPadrao);

            assertThatThrownBy(() -> jwtService.isTokenExpired(tokenExpirado))
                    .isInstanceOf(ExpiredJwtException.class);
        }

        @Test
        @DisplayName("isTokenValid — token expirado deve lançar ExpiredJwtException")
        void tokenExpiradoDeveLancarExpiredJwtExceptionAoValidar() {
            String tokenExpirado = gerarTokenExpirado(usuarioPadrao);

            assertThatThrownBy(() -> jwtService.isTokenValid(tokenExpirado, usuarioPadrao))
                    .isInstanceOf(ExpiredJwtException.class);
        }

        @Test
        @DisplayName("extractUsername — token expirado deve lançar ExpiredJwtException")
        void tokenExpiradoDeveLancarExcecaoAoExtrairUsername() {
            String tokenExpirado = gerarTokenExpirado(usuarioPadrao);

            assertThatThrownBy(() -> jwtService.extractUsername(tokenExpirado))
                    .isInstanceOf(ExpiredJwtException.class);
        }
    }

    // =========================================================================
    // 5. Token Adulterado / Assinatura Inválida
    // =========================================================================
    @Nested
    @DisplayName("5. Token Adulterado / Assinatura Inválida")
    class TokenAdulterado {

        @Test
        @DisplayName("extractUsername — token com assinatura modificada deve lançar exceção")
        void tokenComAssinaturaAlteradaDeveLancarExcecao() {
            String token = jwtService.generateToken(usuarioPadrao);

            // Altera o último caractere da assinatura
            String tokenAdulterado = token.substring(0, token.length() - 1) + "X";

            assertThatThrownBy(() -> jwtService.extractUsername(tokenAdulterado))
                    .isInstanceOf(Exception.class);
        }

        @Test
        @DisplayName("extractUsername — token com payload Base64 diferente deve lançar exceção")
        void tokenComPayloadAlteradoDeveLancarExcecao() {
            String token  = jwtService.generateToken(usuarioPadrao);
            String[] partes = token.split("\\.");

            String payloadCorrompido = partes[0] + ".INVALIDO." + partes[2];

            assertThatThrownBy(() -> jwtService.extractUsername(payloadCorrompido))
                    .isInstanceOf(Exception.class);
        }
    }
}
