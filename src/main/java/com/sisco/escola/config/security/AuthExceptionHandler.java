package com.sisco.escola.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

/**
 * Classe responsável por interceptar e tratar exceções de segurança na camada de requisições.
 * 
 * Implementa duas interfaces fundamentais para APIs REST:
 * 1. {@link AuthenticationEntryPoint}: Captura falhas de autenticação (HTTP 401 Unauthorized), 
 *    como tokens JWT ausentes, malformados ou expirados.
 * 2. {@link AccessDeniedHandler}: Captura falhas de autorização (HTTP 403 Forbidden), 
 *    quando o usuário está autenticado mas não possui a permissão requerida (Role/Authority).
 * 
 * Retorna respostas padronizadas em formato JSON (ao invés das páginas HTML padrão do Spring).
 */
@Component
public class AuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
            throws IOException, ServletException {
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Gera um JSON formatado de erro REST para respostas 401
        String json = String.format(
            "{\"timestamp\":\"%s\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"%s\",\"path\":\"%s\"}",
            Instant.now().toString(),
            "Acesso negado: Credenciais invalidas ou ausentes. Por favor, forneca um token JWT valido no header Authorization.",
            request.getRequestURI()
        );
        
        response.getWriter().write(json);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) 
            throws IOException, ServletException {
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Gera um JSON formatado de erro REST para respostas 403
        String json = String.format(
            "{\"timestamp\":\"%s\",\"status\":403,\"error\":\"Forbidden\",\"message\":\"%s\",\"path\":\"%s\"}",
            Instant.now().toString(),
            "Acesso proibido: Voce nao possui as permissoes necessarias para acessar este recurso.",
            request.getRequestURI()
        );
        
        response.getWriter().write(json);
    }
}
