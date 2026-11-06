package com.sisco_e.escola.api.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class LinkInternetRequestDTO {
	private static final String IPV4_OCTET = "(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)";
	private static final String IPV4_PATTERN = "^(?:" + IPV4_OCTET + "\\.){3}" + IPV4_OCTET + "$";
	private static final String OPTIONAL_IPV4_PATTERN = "^$|" + IPV4_PATTERN;
	private static final String MASK_OCTET = "(?:255|254|252|248|240|224|192|128|0)";
	private static final String NETWORK_MASK_PATTERN = "^(?:(?:255\\.){3}" + MASK_OCTET
			+ "|(?:255\\.){2}" + MASK_OCTET + "\\.0|255\\." + MASK_OCTET + "\\.0\\.0|"
			+ MASK_OCTET + "\\.0\\.0\\.0)$";

	@NotBlank(message = "{link.tipo.notblank}")
	@Pattern(regexp = "FIBRA_OPTICA|RADIO|SATELITE|G5", message = "{link.tipo.invalido}")
	@Size(max = 30, message = "{link.tipo.size}")
	private String tipoLink;

	@NotBlank(message = "{link.ip.wan.notblank}")
	@Pattern(regexp = IPV4_PATTERN, message = "{link.ip.wan.formato}")
	@Size(max = 45, message = "{link.ip.wan.size}")
	private String ipWan;

	@NotBlank(message = "{link.mascara.rede.notblank}")
	@Pattern(regexp = NETWORK_MASK_PATTERN, message = "{link.mascara.rede.formato}")
	@Size(max = 45, message = "{link.mascara.rede.size}")
	private String mascaraRede;

	@NotBlank(message = "{link.gateway.notblank}")
	@Pattern(regexp = IPV4_PATTERN, message = "{link.gateway.formato}")
	@Size(max = 45, message = "{link.gateway.size}")
	private String gateway;

	@NotBlank(message = "{link.dns.primario.notblank}")
	@Pattern(regexp = IPV4_PATTERN, message = "{link.dns.primario.formato}")
	@Size(max = 45, message = "{link.dns.primario.size}")
	private String dnsPrimario;

	@Pattern(regexp = OPTIONAL_IPV4_PATTERN, message = "{link.dns.secundario.formato}")
	@Size(max = 45, message = "{link.dns.secundario.size}")
	private String dnsSecundario;

	@Min(value = 1, message = "{link.vlan.min}")
	@Max(value = 4094, message = "{link.vlan.max}")
	private Integer vlanId;	
}
