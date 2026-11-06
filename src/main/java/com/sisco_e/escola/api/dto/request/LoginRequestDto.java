package com.sisco_e.escola.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
        @Size(min = 8, max = 255, message = "{usuario.senha.size}")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "{usuario.senha.pattern}")
        private String password;
}

/**
 * TODO-LIST
 * [] Verificar erro interno do servidor ao fazer o login
 * **/
