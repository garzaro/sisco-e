package com.sisco_e.escola.api.dto.response;

import java.util.UUID;

public class LinkInternetResponseDTO {
    UUID uuid;
    UUID uuidContratoInternet;
    String tipoLink;
    String ipWan;
    String mascaraRede;
    String gateway;
    String dnsPrimario;
    String dnsSecundario;
    Integer vlanId;
    Boolean isAtivo;
}
