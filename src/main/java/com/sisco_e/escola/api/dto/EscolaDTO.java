package com.sisco_e.escola.api.dto;

import java.util.UUID;

import com.sisco_e.escola.model.entity.TipoEscola;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EscolaDTO {

	private UUID uuid;

	@NotBlank(message = "{escola.nome.notblank}")
	@Size(max = 120)
	private String nomeEscola;

	@NotBlank(message = "{escola.codigo.notblank}")
	@Size(max = 10)
	private String codigoEscola;

	@NotBlank(message = "{escola.municipio.notblank}")
	@Size(max = 120)
	private String municipio;

	@NotBlank(message = "{escola.estado.notblank}")
	@Size(min = 2, max = 2)
	private String estado;	

	@NotBlank
	@Size(max = 255)
	private String logradouro;
	
	@NotBlank
	@Size(max = 255)
	private String bairro;

	@NotBlank
	@Size(max = 8)
	private String cep;

	@NotNull(message = "{tipo.escola.notnull}")
	private TipoEscola tipoEscola;

	private Boolean isAtivo;
}