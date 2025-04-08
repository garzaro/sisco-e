package com.sisco.escola.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EscolaDTO {
    private Long id;
    private String nome;
    private String codigo;
    private String cidade;
    private String bairro;
    private String endereco;
    private String telefone;
}
