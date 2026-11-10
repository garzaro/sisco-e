package com.sisco_e.escola.security;

import com.sisco_e.escola.service.JwtService;
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
import java.util.Optional;

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
    @DisplayName("F-05: token inválido não autentica e continua a cadeia")
    void doFilter_TokenInvalido_NaoAutenticaEContinuaCadeia() throws ServletException, IOException {
        String token = "expirado.token.a";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.obterSubjectAccessToken(token)).thenReturn(Optional.empty());

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("F-08: token de acesso válido autentica com sucesso")
    void doFilter_TokenValido_Autentica() throws ServletException, IOException {
        String token = "valido.token.a";
        String email = "user@test.com";
        UserDetails user = new User(email, "pass", Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.obterSubjectAccessToken(token)).thenReturn(Optional.of(email));
        when(userDetailsService.loadUserByUsername(email)).thenReturn(user);
        when(jwtService.isTokenValid(token, user)).thenReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(user);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("F-13: token de acesso inválido não cria autenticação")
    void doFilter_TokenAcessoInvalido_NaoAutentica() throws ServletException, IOException {
        String token = "token.invalido.a";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.obterSubjectAccessToken(token)).thenReturn(Optional.of("user@test.com"));
        UserDetails user = new User("user@test.com", "pass", Collections.emptyList());
        when(userDetailsService.loadUserByUsername("user@test.com")).thenReturn(user);
        when(jwtService.isTokenValid(token, user)).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("F-14: refresh ou token legado rejeitado pelo validador não autentica")
    void doFilter_TokenNaoAccess_NaoAutentica() throws ServletException, IOException {
        String token = "refresh.token.a";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.obterSubjectAccessToken(token)).thenReturn(Optional.empty());

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("F-12: doFilter deve ser chamado exatamente uma vez em todos os cenários")
    void doFilter_ChamadoUmaVez() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
