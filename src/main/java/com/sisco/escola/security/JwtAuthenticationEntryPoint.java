package com.sisco.escola.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por interceptar e tratar exceções de segurança na camada de requisições.
 * 
 * Implementa duas interfaces fundamentais para APIs REST:
 * 1. {@link AuthenticationEntryPoint}: Captura falhas de autenticação (HTTP 401 Unauthorized), 
 *    como tokens JWT ausentes, malformados ou expirados.
 *    
 * 2. SE FOR USAR UM DIA , AccessDeniedHandler  - {@link AccessDeniedHandler}: Captura falhas de autorização (HTTP 403 Forbidden), 
 *    quando o usuário está autenticado mas não possui a permissão requerida (Role/Authority).
 * 
 * Retorna respostas padronizadas em formato JSON (ao invés das páginas HTML padrão do Spring).
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request,
    		HttpServletResponse response, AuthenticationException authException) 
            throws IOException, ServletException {        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + "charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401

//        // Gera um JSON formatado de erro REST para respostas 401
//        String json = String.format(
//            "{\"timestamp\":\"%s\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"%s\",\"path\":\"%s\"}",
//            Instant.now().toString(),
//            "Acesso negado: Credenciais invalidas ou ausentes. Por favor, forneça um token JWT valido no header Authorization.",
//            request.getRequestURI()
//        );
        final Map<String, Object> corpo = new HashMap<>();
        corpo.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        corpo.put("error","Unauthorized");
        corpo.put("message", authException.getMessage());
        corpo.put("path", request.getServletPath());
        
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(corpo));
    }

//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) 
//            throws IOException, ServletException {
//        
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//
//        // Gera um JSON formatado de erro REST para respostas 403
//        String json = String.format(
//            "{\"timestamp\":\"%s\",\"status\":403,\"error\":\"Forbidden\",\"message\":\"%s\",\"path\":\"%s\"}",
//            Instant.now().toString(),
//            "Acesso proibido: Voce nao possui as permissoes necessarias para acessar este recurso.",
//            request.getRequestURI()
//        );
//        
//        response.getWriter().write(json);
//    }
}
