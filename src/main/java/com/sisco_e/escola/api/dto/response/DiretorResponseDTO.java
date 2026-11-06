package com.sisco_e.escola.api.dto.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiretorResponseDTO {
    private UUID uuid;

    private UUID escolaUuid;

    private String nomeDiretor;

    private String cpf;

    private String matriculaFuncional;

    private String email;

    private String emailPessoal;

    private Instant dataPosse;

    private Instant dataFimMandato;

    private Boolean isAtivo;
}
