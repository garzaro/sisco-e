package com.sisco_e.escola.security;

import java.io.IOException;

import com.sisco_e.escola.service.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Filtro de Requisição JWT (JwtFilter)
 * O filtro intercepta cada requisição HTTP, verifica se o cabeçalho Authorization contém
 * um token Bearer, valida-o e injeta a autenticação no contexto do Spring Security.
 * A sequência principal está correta
 * sequência lógica do filtro:
 * [x] Lê o cabeçalho Authorization.
 * [x] Verifica o prefixo Bearer .
 * [x] Extrai o JWT do cabeçalho Authorization.
 * [x] Obtém o identificador do usuário.
 * [x] Carrega o usuário pelo UserDetailsService.
 * [x] Valida o token.
 * [x] Cria o objeto Authentication.
 * [x] Coloca a autenticação no SecurityContext.
 * [x] Continua a cadeia de filtros.
 *
 * Filtro JWT
 * 1. Recebe a requisição.
 * 2. Lê o cabeçalho Authorization.
 * 3. Valida assinatura e expiração.
 * 4. Configura o SecurityContext.
 * 5. Permite ou nega o acesso.
 * **/

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(BEARER_PREFIX.length());
        var subject = jwtService.obterSubjectAccessToken(token);
        if (subject.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(subject.get());
                if (jwtService.isTokenValid(token, userDetails)) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    context.setAuthentication(authentication);
                }
            } catch (UsernameNotFoundException ex) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
