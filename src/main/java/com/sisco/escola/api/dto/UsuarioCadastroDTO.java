package com.sisco.escola.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Builder
@Getter
@Setter
public class UsuarioCadastroDTO {
    private Long id;
    private String nomeCompleto;
    private String cadastroPessoaFisica;
    private String nomeUsuario;
    private String email;
    private String senha;
    private LocalDate dataCadastro;
}
