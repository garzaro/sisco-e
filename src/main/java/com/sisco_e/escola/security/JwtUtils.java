package com.sisco_e.escola.security;

/**
 * JWTUtils
 * 
 * GERAR, GERENCIAR E TRABALHAR COM JWT
 * 
 * Contem mtodos utilitarios pra gerar, parsear, e validar JWTs.
 * Inclui gerar um token de um username, validar o o JTW, e extrair o username
 * de um token.
 * **/

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

import java.security.Key;
import java.util.Date;
//
//@Component
//public class JwtUtils {
//	/**registrador**/
//	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
//
//	/**chave que será utilizada para assinar os tokens**/
//	@Value("${spring.app.jwtSecretKey}") //como isso é uma busca precisa criar no application.properties
//	private String jwtSecretKey;
//
//	/**o token expira em milissegundos**/
//	@Value("${spring.app.jwtExpirationMs}") //como isso é uma busca precisa criar no application.properties
//	private long jwtExpirationMs; //coloquei long aqui, se alguem intromenter no env o sistema falha e nao vai funcionar
//
//	/**obter o token do cabecalho http
//	 * Extrai o token e o retorna em formato de string
//	 * **/
//	public String getJwtFromHeader(HttpServletRequest request) {
//		/**estou obtendo a solicitacao (request) e o nome do cabeçalho é Authorization**/
//		String bearerToken = request.getHeader("Authorization");
//		logger.debug("Authorization Header: {}", bearerToken);
//
//		/**alem de nao nulo verifico se o token do portador começa com o espaço do portador**/
//		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//			/**se verdadeiro exclui "Bearer", retorna apenas o token **/
//			return bearerToken.substring(7); // retorna  string e remove o prefixo do Bearer
//		}
//		/**caso contrario retorna nulo**/
//		return null;
//	}
//
//	/**@param userDetails (codificando) - objeto de detalhes do usuario.
//	 * Pego o usuario a partir dos detalhes do usuario
//	 * **/
//	public String gerarTokenDoNomeDeUsuário(UserDetails userDetails) {
//
//		String username = userDetails.getUsername();
//
//		return Jwts.builder()
//				.subject(username) //CLAIM
//				.issuedAt(new Date())
//				.expiration(new Date((new Date()).getTime() + jwtExpirationMs))
//				.signWith(key())
//				.compact();
//	}
//
//	/**@param token (decodificando o que foi gerado com userdetails) - objeto para
//	 * obter o nome de usuario do token
//	 * **/
//	public String pegarNomeDeUsuarioDoToken(String token) {
//		return Jwts
//				.parser()
//				.verifyWith((SecretKey) key())
//				.build()
//				.parseSignedClaims(token)
//				.getPayload() //da carga util ...
//				.getSubject(); //... pega o objeto
//	}
//
//	/**configurar a chave para assinar o JWT**/
//	private Key key() {
//		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
//	}
//
//	public boolean validarTokenJwt(String authToken) {
//		try {
//			System.out.println("Validacao");
//			Jwts
//			.parser()
//			.verifyWith((SecretKey) key())
//			.build() //constroi isso
//			.parseSignedClaims(authToken);
//			return true;
//
//		} catch (MalformedJwtException e) {
//			logger.error("Token JWt inválido: {}", e.getMessage());
//
//		} catch (ExpiredJwtException e) {
//			logger.error("Token JWT expirado: {}", e.getMessage());
//
//		} catch (UnsupportedJwtException e) {
//			logger.error("JWT não suportado: {}", e.getMessage());
//
//		} catch (IllegalArgumentException e) {
//			logger.error("A string de declarações JWT está vazia.: {}"); //claims string
//
//		}
//
//		return false;
//	}
//}
