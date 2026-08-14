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
public class LinkInternetDTO {

	private UUID uuid;
	private UUID uuidContratoInternet;
	private String tipoLink;
	private String ipWan;
	private String mascaraRede;
	private String gateway;
	private String dnsPrimario;
	private String dnsSecundario;
	private Integer vlanId;
	private Boolean isAtivo;

}
