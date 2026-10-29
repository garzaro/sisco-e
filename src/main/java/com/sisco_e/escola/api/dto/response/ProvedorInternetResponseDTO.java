package com.sisco_e.escola.api.dto.response;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvedorInternetResponseDTO {
    UUID uuid;

    String nomeProvedor;

    String cnpj;

    String telefone;
}
