package com.sisco.escola.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EscolaDTO {
    private Long id;
    private String nome;
    private String codigo;
    private String email;
    private String estado;
    private String municipio;
    private String bairro;
    private String endereco;
    private String telefone;
    private LocalDate dataCadastro;
}
