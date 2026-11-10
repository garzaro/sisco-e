package com.sisco_e.escola.api.dto;

/** O usuário ao qual pertencia um token rotacionado,
 *  e o token de substituição a ser devolvido ao cliente.
 *  */
public record Rotation(
        String username,
        String newRefreshToken
) {}
