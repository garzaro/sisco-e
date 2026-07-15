package com.sisco.escola.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contrato para o serviço de processamento e gerenciamento de tokens JWT
 */
public interface JwtServiceProvider {
	
	/**
     * Extrai o subject (email/username) contido no token JWT
     *
     * @param token String do token JWT
     * @return String contendo o email
     */
    String extrairUsernameToken(String token);

    /**
     * Gera um token JWT contendo apenas o subject (email/username)
     *
     * @param username/email identificador do usuário
     * @return String contendo o JWT compactado
     */
    String gerarToken(UserDetails userDetails);

    /**
     * Gera um token JWT com subject e claims personalizadas adicionais
     *
     * @param email    identificador do usuário
     * @param claims claims adicionais que serão adicionadas ao payload
     * @return String contendo o JWT compactado
     */
    String gerarTokenComClaims(Map<String, Object> extraClaims, UserDetails userDetails);

    

    /**
     * Valida se o token JWT é estruturalmente correto, possui assinatura íntegra e não está expirado
     *
     * @param token String do token JWT
     * @param userDetails 
     * @return true se o token for válido e utilizável, false caso contrário
     */
    boolean isTokenValido(String token, UserDetails userDetails);
}
