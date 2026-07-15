package com.sisco.escola.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Record imutável para requisição de login.
 * Validação ocorre via Bean Validation (@Valid no controller).
 */
public record LoginRequestDTO(
        @NotBlank(message = "Email não pode estar vazio")
        @Email(message = "Email deve ser válido")
        String email,

        @NotBlank(message = "Senha não pode estar vazia")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha
) {}
