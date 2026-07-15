package com.sisco.escola.api.dto;

/**
 * Record imutável para resposta de autenticação (login).
 * Contém o token JWT e seu tipo.
 *
 * @param token      Token JWT compactado
 * @param tokenType  Tipo do token (sempre "Bearer")
 */
public record AuthResponseDTO(
        String token,
        String tokenType
) {}
