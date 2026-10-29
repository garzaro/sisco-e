package com.sisco_e.escola.api.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ContratoInternetRequestDTO {

	@NotBlank(message = "{contrato.velocidade.notblank}")
	private String velocidade;

	@Positive(message = "{contrato.valor.mensal.maior.que.zero}")
	@NotNull(message = "{contrato.valor.mensal.notnull}")
	private BigDecimal valorMensal;

 @NotNull(message = "{contrato.data.contratacao.notblank}")
	private LocalDate dataContratacao;
	private LocalDate dataFimContrato;

	@NotNull(message = "{contrato.uuid.escola.notnull}")
	private UUID uuidEscola;

	@NotNull(message = "{contrato.uuid.provedor.notnull}")
	private UUID uuidProvedor;
}
