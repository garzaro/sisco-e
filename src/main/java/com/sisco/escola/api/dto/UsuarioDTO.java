package com.sisco.escola.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * O @Requestbody no controller faz desseriealizacao (converte JSON para entity),
 * JSON para este objeto, java type, de entrada da api
 * **/
@Builder
@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String usuario;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
}
