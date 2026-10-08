package com.sisco_e.escola.service;

import com.sisco_e.escola.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contrato do serviço JWT da aplicação.
 * Responsabilidades:
 * Geração de tokens assinados com HMAC-SHA
 * Extração e validação de claims
 * Verificação de assinatura, ISSUER e expiração
 * Retornar o login do usuario
 * O {@link com.cleber.financas.security.JwtAuthenticationFilter} consome este serviço
 * para autenticar cada requisição.
 */
public interface JwtService {

    /**
     * Gera e retorna um token JWT com dado do usuario como subject e outras infomrações.
     * Usado internamente pelo filtro de autenticação e pelo endpoint de login.
     */
    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    /**
     * retornar todas as informações contidas no token, - subject, nome, id, etc...
     * ao decodificar verifica se o usuario possui acesso a api
     **/
    Claims obterClaims(String token);

    /**
     * Retorna {@code true} se o token for válido, não expirado e pertencer ao usuário.
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * atraves do token retorna detalhes do usaurio que esta tentando acessar a aplicação
     * douglas colocuu String
     **/
    String obterUserDetailsLogin(String token);
}