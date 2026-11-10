package com.sisco_e.escola.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** O par de tokens retornado por signin, signup e refresh. **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    @Schema(description = "JWT de curta duração para envio no cabeçalho 'Authorization': Bearer <token>'")
    private String accessToken;

    @Schema(description = "Token opaco de longa duração usado para obter um novo token de acesso")
    private String refreshToken;

    @Schema(description = "Esquema de autenticação com o qual o token de acesso é utilizado", example = "Bearer")
    private String tokenType;

    @Schema(description = "Tempo de vida do token de acesso, em segundos")
    private long expiresIn;
}
