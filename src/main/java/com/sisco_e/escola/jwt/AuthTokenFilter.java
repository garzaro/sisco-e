package com.sisco_e.escola.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**Isso é um filtro para interceptar cada solicitação para verificar se ela é autenticada**/

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	/**obter a solicitação a resposta e a cadeia de filtros**/
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.debug("AuthTokenFilter chamado pela URI: {}", request.getRequestURI());
		
		try {
			String jwt = parseJwt(request); // estou analisando e extraindo jwt aqui
			if (jwt != null && jwtUtils.validarTokenJwt(jwt)) {
				String username = jwtUtils.pegarNomeDeUsuarioDoToken(jwt);
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username); //validação
				
				UsernamePasswordAuthenticationToken autenticacao = 
						new UsernamePasswordAuthenticationToken(userDetails, null,
								userDetails.getAuthorities());
				
				logger.debug("Funções do JWT: {}", userDetails.getAuthorities());
				
				/**um aprimoramento aqui com detalhes adicionais da solicitação, como ID de sessao **/
				autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				/**pegando objeto e definindo o contexto de seguranca, autenticando o usuario 
				 * efetivamente durante a solicitacao
				 * **/
				SecurityContextHolder.getContext().setAuthentication(autenticacao);
				
			}
		} catch (Exception e) {
			logger.error("Não é possível definir a autenticação do usuário. {}", ALREADY_FILTERED_SUFFIX);
		}
		
		/**
		 * @param Parâmetros:
		 * request - a requisição a ser passada adiante na cadeia.
		 * response - a resposta a ser passada adiante na cadeia.
		 * 
		 * Estou pedindo para continuar com a cadeia de filtro
		 * **/
		filterChain.doFilter(request, response);
	}
	
	/**para analisar e extrair o jwt**/
	private String parseJwt(HttpServletRequest request) {
		String jwt = jwtUtils.getJwtFromHeader(request); //passando o objeto da solitacao pra cá
		/**register**/
		logger.debug("AuthTokenFilter.java: {}", jwt);
		return jwt;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
