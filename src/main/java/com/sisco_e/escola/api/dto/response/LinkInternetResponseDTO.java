package com.sisco_e.escola.api.dto.response;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkInternetResponseDTO {
    private UUID uuid;

    private UUID contratoInternetUuid;

    private UUID provedorInternetUuid;

    private UUID escolaUuid;

    private String tipoLink;

    private String ipWan;

    private String mascaraRede;

    private String gateway;

    private String dnsPrimario;

    private String dnsSecundario;

    private Integer vlanId;

    private Boolean isAtivo;
}
