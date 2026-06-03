package com.sisco.escola.service;

import java.util.Map;

/**
 * Contrato para o serviço de processamento e gerenciamento de tokens JWT.
 */
public interface JwtService {

    /**
     * Gera um token JWT contendo apenas o subject (username/email).
     *
     * @param username identificador do usuário
     * @return String contendo o JWT compactado
     */
    String gerarToken(String username);

    /**
     * Gera um token JWT com subject e claims personalizadas adicionais.
     *
     * @param username    identificador do usuário
     * @param claimsExtra claims adicionais que serão adicionadas ao payload
     * @return String contendo o JWT compactado
     */
    String gerarToken(String username, Map<String, Object> claimsExtra);

    /**
     * Extrai o subject (username/email) contido no token JWT.
     *
     * @param token String do token JWT
     * @return String contendo o username
     */
    String obterUsername(String token);

    /**
     * Valida se o token JWT é estruturalmente correto, possui assinatura íntegra e não está expirado.
     *
     * @param token String do token JWT
     * @return true se o token for válido e utilizável, false caso contrário
     */
    boolean isTokenValido(String token);
}
