package com.sisco_e.escola.api.dto.response;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private UUID uuid;

    private String nomeCompleto;

    private String cpf;

    private String username;

    private String email;

    private Boolean isAtivo;
}
