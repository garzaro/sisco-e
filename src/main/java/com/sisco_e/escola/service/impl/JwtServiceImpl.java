package com.sisco_e.escola.service.impl;

import com.sisco_e.escola.model.entity.Usuario;
import com.sisco_e.escola.service.JwtService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public class JwtServiceImpl implements JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    /** Identifica o emissor — validado no parse para evitar tokens de outros sistemas. */
    private static final String ISSUER = "financas-api";

    @Override
    public String gerarToken(UserDetails userDetails) {
        return "";
    }

    @Override
    public String gerarToken(Usuario usuario) {
        return "";
    }

    @Override
    public String gerarTokenComClaims(Map<String, Object> extraClaims, UserDetails userDetails) {
        return "";
    }

    @Override
    public String extrairUsernameToken(String token) {
        return "";
    }

    @Override
    public String getUserLogin(String token) {
        return "";
    }

    @Override
    public boolean isTokenValido(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public Claims obterClaims(String token) {
        return null;
    }
}
