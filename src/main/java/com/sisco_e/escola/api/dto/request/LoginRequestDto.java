package com.sisco_e.escola.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto{
        @NotBlank(message = "{usuario.email.notblank}")
        @Email(message = "{usuario.email.valido}")
        private String email;

        @NotBlank(message = "{usuario.senha.notblank}")
        @Size(min = 6, message = "{usuario.senha.size}")
        private String password;
}

/**
 * TODO-LIST
 * [] Verificar erro interno do servidor ao fazer o login
 * **/