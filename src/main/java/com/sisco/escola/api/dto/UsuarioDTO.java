package com.sisco.escola.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.sisco.escola.validacao.CpfValido;
import java.time.Instant;

/**
 * O @Requestbody no controller faz desseriealizacao (converte JSON para entity),
 * JSON para este objeto, java type, de entrada da api
 * **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;

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
    private String email;

    @NotBlank(message = "{usuario.senha.notblank}")
    @Size(min = 8, max = 32, message = "{usuario.senha.size}")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*\\d)(?=.*[@$!%*?&])" +
                    "[A-Za-z\\d@$!%*?&]{6,}$",
            message = "{usuario.senha.pattern}"
    )
    private String password;

    private Instant dataCadastro;

    private Boolean ativo;
}
