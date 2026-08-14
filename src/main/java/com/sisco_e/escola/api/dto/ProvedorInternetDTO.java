package com.sisco_e.escola.api.dto;

import java.util.UUID;

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
	private String nomeProvedor;
	private String cnpj;
	private String telefone;

}
