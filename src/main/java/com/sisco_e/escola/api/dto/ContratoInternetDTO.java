package com.sisco_e.escola.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.sisco_e.escola.model.enums.StatusContrato;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoInternetDTO {

	private UUID uuid;
	private UUID uuidEscola;
	private UUID uuidProvedor;
	private LocalDate dataContratacao;
	private LocalDate dataFimContrato;
	@NotBlank
	private String velocidade;
	private BigDecimal valorMensal;
	private StatusContrato status;

}
