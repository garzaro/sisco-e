package com.sisco.escola.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import validator_domain_core.validation.AllowedDomain;

import com.sisco.escola.validacao.CpfValido;
import java.time.Instant;
import java.util.UUID;

/**
 * O @Requestbody no controller faz desseriealizacao (converte JSON para entity),
 * JSON para este objeto, java type, de entrada da api
 * **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private UUID id;

    @NotBlank(message = "{usuario.nome.notblank}")
    @Size(max = 100, message = "{usuario.nome.size}")
    private String nome;

    @NotBlank(message = "{usuario.cpf.notblank}")
    @Size(max = 14)
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "{usuario.cpf.pattern}")
    @CpfValido
    private String cpf;

    @NotBlank(message = "{usuario.username.notblank}")
    @Size(max = 50, message = "{usuario.username.size}")
    private String usuario;

    @NotBlank(message = "{usuario.email.notblank}")
    @Email(message = "{usuario.email.valid}")
    @Size(max = 150, message = "{usuario.email.size}")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-z]{2,}$")
    @AllowedDomain
    private String email;

    /*
     * [] Pelo menos uma letra minúscula ((?=.*[a-z])).
     * [] Pelo menos uma letra maiúscula ((?=.*[A-Z])).
     * [] Pelo menos um dígito ((?=.*\d)).
     * [] Pelo menos um caractere especial ((?=.*[@$!%*?&])).
     * [] Comprimento mínimo de 6 caracteres ({8,}).
    */
    @NotBlank(message = "{usuario.senha.notblank}")
    @Size(min = 8, max = 32, message = "{usuario.senha.size}")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*\\d)(?=.*[@$!%*?&])" +
                    "[A-Za-z\\d@$!%*?&]{6,}$",
            message = "{usuario.senha.pattern}"
    )
    
    @NotBlank(message = "{usuario.senha.notblank}")
    @Size(min = 8, max = 32, message = "{usuario.senha.size}")
    private String senha;

    private Instant dataCadastro;

    private Boolean ativo;
}
