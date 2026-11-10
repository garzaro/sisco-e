package com.sisco_e.escola.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Corpo das requisições de atualização (refresh) e de encerramento de sessão (logout). */
@Data
@NoArgsConstructor
public class RefreshRequestDTO {
    @Schema(description = "O token de atualização emitido juntamente com o token de acesso atual")
    @NotBlank
    private String refreshToken;
}
