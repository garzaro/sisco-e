package com.sisco_e.escola.api.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}
