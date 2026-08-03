package com.sisco_e.escola.api.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        
        @NotBlank(message = "{usuario.nome.notblank}")
        @Size(max = 120, message = "{usuario.nome.size}")
        String nomeCompleto,
        
        @NotBlank(message = "{usuario.cpf.notblank}")
        @CPF(message = "{usuario.cpf.invalido}")
        @Size(max = 11, message = "{usuario.cpf.size}")
        //    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "{usuario.cpf.pattern}")
        String cpf,

        @NotBlank(message = "{usuario.username.notblank}")
        @Size(max = 120, message = "{usuario.username.size}")
        String username,

        @NotBlank(message = "{usuario.email.notblank}")
        @Email(message = "{usuario.email.valid}")
        @Size(max = 120, message = "{usuario.email.size}")
        @Pattern(regexp = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-z]{2,}$")
//    @AllowedDomain - refazer o anotation
        String email,

        @NotBlank(message = "{usuario.senha.notblank}")
        @Size(min = 6, max = 255, message = "{usuario.senha.size}")
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*\\d)(?=.*[@$!%*?&])" +
                    "[A-Za-z\\d@$!%*?&]{6,}$",
            message = "{usuario.senha.pattern}"
        )
        String password,

        Boolean isAtivo
        
) {}
