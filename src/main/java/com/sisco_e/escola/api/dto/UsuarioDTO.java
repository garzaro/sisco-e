package com.sisco_e.escola.api.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import validator_domain_core.validation.AllowedDomain;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	private UUID uuid;

    @NotBlank(message = "{usuario.nome.notblank}")
    @Size(max = 120, message = "{usuario.nome.size}")
    private String nomeCompleto;
        
    @NotBlank(message = "{usuario.cpf.notblank}")
    @Size(max = 14)
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "{usuario.cpf.pattern}")
//    @CpfValido
    private String cpf;

    @NotBlank(message = "{usuario.username.notblank}")
    @Size(max = 120, message = "{usuario.username.size}")
    private String username;

    @NotBlank(message = "{usuario.email.notblank}")
    @Email(message = "{usuario.email.valid}")
    @Size(max = 120, message = "{usuario.email.size}")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-z]{2,}$")
//    @AllowedDomain - refazer o anotation
    private String email;

    @NotBlank(message = "{usuario.senha.notblank}")
    @Size(min = 8, max = 120, message = "{usuario.senha.size}")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*\\d)(?=.*[@$!%*?&])" +
                    "[A-Za-z\\d@$!%*?&]{6,}$",
            message = "{usuario.senha.pattern}"
    )
    private String password;   

}
