package com.sisco_e.escola.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.sisco_e.escola.model.enums.StatusContrato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

	@NotNull(message = "{contrato.uuid.escola.notnull}")
	private UUID uuidEscola;

	@NotNull(message = "{contrato.uuid.provedor.notnull}")
	private UUID uuidProvedor;

	@NotNull(message = "{contrato.data.contratacao.notblank}")
	private LocalDate dataContratacao;
	private LocalDate dataFimContrato;

	@NotBlank(message = "{contrato.velocidade.notblank}")
	private String velocidade;
	private BigDecimal valorMensal;
	private StatusContrato status;
}
