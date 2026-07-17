package com.sisco.escola.security;

import com.sisco.escola.service.JwtServiceProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de segurança customizado interceptador de requisições HTTP (stateless).
 * CRIAÇÃO DO OBJETO DE AUTENTICAÇÃO
 * É executado uma vez por requisição ({@link OncePerRequestFilter}) e tem a responsabilidade de:
 * Extrair o token JWT do cabeçalho "Authorization: Bearer <token>".
 * Validar a assinatura e a expiração do token.
 * Se válido, buscar os detalhes do usuário ({@link UserDetails}) via {@link UserDetailsService}.
 * Injetar a autenticação válida no contexto de segurança global ({@link SecurityContextHolder}) do Spring Security.
 */
@Component
@RequiredArgsConstructor 
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";

	private final JwtServiceProvider jwtServiceProvider;
	private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
    		HttpServletRequest request,
    		HttpServletResponse response,
    		FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        /**Se o cabeçalho Authorization não estiver presente ou não for do tipo Bearer, segue a cadeia sem autenticar**/
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        /**
         * Uso de {@code substring(7)}, pode causar erro se o prefixo for alterado
         * @param .lenght = {@code .lenght} se hover alteração no tamanho do prefixo nao causará erro
         * **/
        final String jwt = authHeader.substring(BEARER_PREFIX.length()); //(7)

        final String pegaEmailDoUsuario;
        try {
            /**Extrai o username/email embutido no token JWT -jwt**/
            pegaEmailDoUsuario = jwtServiceProvider.extrairUsernameToken(jwt); //.obterEmailFromToken(jwt);
        } catch (ExpiredJwtException | MalformedJwtException ex) {
        	/**Log crítico: token malformado/expirado é um evento de segurança relevante**/
            logger.warn("Token inválido ou expirado: {} ", ex.getMessage());
            /**Continua a execução para o próximo filtro na cadeia de filtros do Spring Security**/
            filterChain.doFilter(request, response);
            return;
        }

       /**Só autentica se houver email e o contexto ainda não tiver autenticação**/
        SecurityContext context = SecurityContextHolder.getContext();
        if(pegaEmailDoUsuario != null && context.getAuthentication() == null) {
        	/**Carrega os dados do usuário a partir da base (garante que ele existe e está ativo)**/ 
        	UserDetails userDetails = userDetailsService.loadUserByUsername(pegaEmailDoUsuario);
        	/**o que ta na base e o extraido deve ser igual**/
        	if (jwtServiceProvider.isTokenValido(jwt, userDetails)) {
        		UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                        		null, // credentials nulas: já autenticado via token, não via senha
                                userDetails.getAuthorities()
        	        	);

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                /**Popula o contexto — daqui pra frente a requisição é tratada como autenticada**/
        		context.setAuthentication(authToken);        		
        	}        	
        }
        filterChain.doFilter(request, response);
    }
}
