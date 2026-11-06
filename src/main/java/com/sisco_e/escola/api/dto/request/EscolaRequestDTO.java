package com.sisco_e.escola.api.dto.request;

import java.util.UUID;

import com.sisco_e.escola.model.enums.TipoEscola;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *todo-LIST
 * [] Tipo escola nulo deve validar o campo nulo ou vazio ou tipo inxistente, Handler lanca (postman) mas dificulta a analise do bug
 * **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscolaRequestDTO {

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
	@Size(min = 2, max = 2, message = "{escola.uf.tamanho}")
	private String estado;	

	@NotBlank(message = "{escola.logradouro.notblank}")
	@Size(max = 255, message = "{escola.logradouro.size}")
	private String logradouro;
	
	@NotBlank(message = "{escola.bairro.notblank}")
	@Size(max = 255, message = "{escola.bairro.size}")
	private String bairro;

	@NotBlank(message = "{escola.cep.notblank}")
	@Size(min = 8, max = 8, message = "{escola.cep.size}")
	@Pattern(regexp = "^[0-9]{8}$", message = "{escola.cep.formato}")
	private String cep;
	
	@NotNull(message = "{escola.tipo.escola.notnull}")
	private TipoEscola tipoEscola;

}
