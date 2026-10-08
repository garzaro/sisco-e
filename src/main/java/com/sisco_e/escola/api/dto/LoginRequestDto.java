package com.sisco_e.escola.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank(message = "{usuario.email.notblank}")
        @Email(message = "{usuario.email.valido}")
        String email,
        
        @NotBlank(message = "{usuario.senha.notblank}")
        @Size(min = 6, message = "{usuario.senha.size}")
        String password
) {}

/**
 * TODO-LIST
 * [] Verificar erro interno do servidor ao fazer o login
 * **/