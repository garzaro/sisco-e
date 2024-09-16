package com.sisco.escola.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EscolaDTO {
    
    /*private Long id;*/
    private String nomeEscola;
    private String codigoEscola;
    private String cidadeEscola;
    private String bairroEscola;
    private String endereco;
    private String telefone;
    
}
