package com.sisco_e.escola.service;

import com.sisco_e.escola.model.entity.RefreshToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(UserDetails userDetails);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);

    void deleteByEmail(String email);

    void deleteByToken(String token);
}
