package com.sisco.escola.config.security;

import com.sisco.escola.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de segurança customizado interceptador de requisições HTTP (stateless).
 * 
 * É executado uma vez por requisição ({@link OncePerRequestFilter}) e tem a responsabilidade de:
 * 1. Extrair o token JWT do cabeçalho "Authorization: Bearer <token>".
 * 2. Validar a assinatura e a expiração do token.
 * 3. Se válido, buscar os detalhes do usuário ({@link UserDetails}) via {@link UserDetailsService}.
 * 4. Injetar a autenticação válida no contexto de segurança global ({@link SecurityContextHolder}) do Spring Security.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Se o cabeçalho Authorization não estiver presente ou não for do tipo Bearer, segue a cadeia sem autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        
        try {
            // Extrai o username/email embutido no token JWT
            userEmail = jwtService.obterUsername(jwt);

            // Se o token for válido e o contexto de autenticação atual ainda estiver vazio.
            // O próprio parser de JWT no obterUsername garante a integridade da assinatura e que não expirou.
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Carrega os dados do usuário a partir da base (garante que ele existe e está ativo)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Injeta a autenticação no contexto do Spring Security, liberando o acesso às rotas protegidas
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            // Em caso de falhas na validação do token (expirado, corrompido, etc.),
            // a requisição continuará como anônima e será capturada pelo AuthExceptionHandler.
            logger.warn("Erro no processamento do token JWT: " + e.getMessage());
        }

        // Continua a execução para o próximo filtro na cadeia de filtros do Spring Security
        filterChain.doFilter(request, response);
    }
}
