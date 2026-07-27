package com.sisco_e.escola.api.dto;

public record AuthResponse(
        String token,
        String tokenType
) {}
