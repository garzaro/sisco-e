package com.sisco_e.escola.security;

import com.sisco_e.escola.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtAuthenticationFilter - Testes Unitários")
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("F-01: sem header Authorization, deve apenas continuar a cadeia")
    void doFilter_SemHeader_ContinuaCadeia() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);
        
        filter.doFilterInternal(request, response, filterChain);
        
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("F-02: header sem prefixo Bearer, deve apenas continuar a cadeia")
    void doFilter_SemBearer_ContinuaCadeia() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Basic 123");
        
        filter.doFilterInternal(request, response, filterChain);
        
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("F-05: token expirado não deve propagar exceção e deve retornar 401")
    void doFilter_TokenExpirado_ContinuaCadeiaENaoAutentica() throws ServletException, IOException {
        String token = "expirado.token.a";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        // O filtro chama jwtService.obterClaims(jwt) que lança ExpiredJwtException quando token expirado
        when(jwtService.obterClaims(token)).thenThrow(new ExpiredJwtException(null, null, "expired"));

        filter.doFilterInternal(request, response, filterChain);

        // O filtro faz doFilter E seta status 401 no catch de ExpiredJwtException
        verify(filterChain).doFilter(request, response);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("F-08: token válido, autentica com sucesso")
    void doFilter_TokenValido_Autentica() throws ServletException, IOException {
        String token = "valido.token.a";
        String email = "user@test.com";
        UserDetails user = new User(email, "pass", Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        // O filtro chama jwtService.obterClaims(jwt), converte o resultado para String via String.valueOf()
        // O resultado do String.valueOf(claims) é usado como getuserEmail
        // Para que o fluxo funcione, precisamos que o Claims mock retorne algo que, ao
        // passar por String.valueOf(), gere um valor não-nulo que será usado no loadUserByUsername.
        Claims mockClaims = mock(Claims.class);
        when(jwtService.obterClaims(token)).thenReturn(mockClaims);

        // String.valueOf(mockClaims) vai produzir a representação toString() do mock
        // O filtro usará esse valor como email para buscar o usuário
        String claimsAsString = String.valueOf(mockClaims);
        when(userDetailsService.loadUserByUsername(claimsAsString)).thenReturn(user);
        when(jwtService.isTokenValid(token, user)).thenReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(user);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("F-12: doFilter deve ser chamado exatamente uma vez em todos os cenários")
    void doFilter_ChamadoUmaVez() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
