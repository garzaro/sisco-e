package com.sisco_e.escola.api.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
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
public class ProvedorInternetDTO {

	private UUID uuid;
	
	@NotBlank(message = "{provedor.nome.notblank}")
	@Size(max = 120, message = "{provedor.nome.size}")
	private String nomeProvedor;
	
	@NotBlank(message = "{provedor.cnpj.notblank}")
	@CNPJ(message = "{provedor.cnpj.invalido}")
	@Size(min = 14, max = 14, message = "{provedor.cnpj.size}")
	private String cnpj;
	
	@NotBlank(message = "{provedor.telefone.notblank}")
	@Size(min= 10, max= 11, message = "{provedor.telfone.size}")	
	private String canalSuportePrioritario;

}
