package com.sisco_e.escola.api.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiretorResponseDTO {

    UUID uuid;

    String nomeDiretor;

    String cpf;

    String matriculaFuncional;

    String email;

    String emailPessoal;

    Instant dataPosse;

    Instant dataFimMandato;

    Boolean isAtivo;

    UUID escolaUuid;
}
