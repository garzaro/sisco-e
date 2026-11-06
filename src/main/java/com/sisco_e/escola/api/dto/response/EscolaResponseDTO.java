package com.sisco_e.escola.api.dto.response;

import com.sisco_e.escola.model.enums.TipoEscola;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscolaResponseDTO {
    private UUID uuid;

    private String nomeEscola;

    private String codigoEscola;

    private String municipio;

    private String estado;

    private String logradouro;

    private String bairro;

    private String cep;

    private TipoEscola tipoEscola;

    private Boolean isAtivo;
}
