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
    UUID uuid;

    String nomeCompleto;

    String cpf;

    String username;

    String email;

    Boolean isAtivo;
}
