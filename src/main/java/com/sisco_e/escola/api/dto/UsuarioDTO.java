package com.sisco_e.escola.api.dto;
 

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import com.alowed_domain.validation.AllowedDomains;


/**
 * Todo: Implementar a validação de domínio de e-mail usando a anotação @AllowedDomains
 * **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    @NotBlank(message = "{usuario.nome.notblank}")
    @Size(max = 120, message = "{usuario.nome.size}")
    private String nomeCompleto;

    @NotBlank(message = "{usuario.cpf.notblank}")
    @CPF(message = "{usuario.cpf.invalido}")
    @Size(max = 11, message = "{usuario.cpf.size}")
    private String cpf;

    @NotBlank(message = "{usuario.username.notblank}")
    @Size(max = 120, message = "{usuario.username.size}")
    String username;

    @NotBlank(message = "{usuario.email.notblank}")
    @Email(message = "{usuario.email.valido}")
    @Size(max = 120, message = "{usuario.email.size}")
    @AllowedDomain
    String email;

    @NotBlank(message = "{usuario.senha.notblank}")
    @Size(min = 6, max = 255, message = "{usuario.senha.size}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])" +
            "(?=.*\\d)(?=.*[@$!%*?&])" +
            "[A-Za-z\\d@$!%*?&]{6,}$",
            message = "{usuario.senha.pattern}")
    String password;
    Boolean isAtivo;
}
