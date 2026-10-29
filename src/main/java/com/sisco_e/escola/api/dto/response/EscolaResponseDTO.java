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
    UUID uuid;

    String nomeEscola;

    String codigoEscola;

    String municipio;

    String estado;

    String logradouro;

    String bairro;

    String cep;

    TipoEscola tipoEscola;

    Boolean IsAtivo;
}
