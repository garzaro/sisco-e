package com.sisco_e.escola.api.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoInternetResponseDto {
    private UUID uuid;

    private UUID uuidEscola;

    private UUID uuidProvedor;

    private String velocidade;

    private BigDecimal valorMensal;

    private LocalDate dataContratacao;

    private LocalDate dataFimContrato;
}
