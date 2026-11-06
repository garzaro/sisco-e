package com.sisco_e.escola.api.dto.request;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class ProvedorInternetRequestDTO {

	@NotBlank(message = "{provedor.nome.notblank}")
	@Size(max = 120, message = "{provedor.nome.size}")
	private String nomeProvedor;
	
	@NotBlank(message = "{provedor.cnpj.notblank}")
	@CNPJ(message = "{provedor.cnpj.invalido}")
	@Size(min = 14, max = 14, message = "{provedor.cnpj.size}")
	private String cnpj;
	
	@NotBlank(message = "{provedor.telefone.notblank}")
	@Size(min = 10, max = 11, message = "{provedor.telefone.size}")
	@Pattern(regexp = "^[0-9]{10,11}$", message = "{provedor.telefone.formato}")
	private String telefone;
}
