package com.sisco_e.escola.api.dto.response;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvedorInternetResponseDTO {
    private UUID uuid;

    private String nomeProvedor;

    private String cnpj;

    private String telefone;
}
