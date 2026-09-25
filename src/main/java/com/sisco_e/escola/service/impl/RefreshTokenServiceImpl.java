package com.sisco_e.escola.service.impl;

import com.sisco_e.escola.model.entity.RefreshToken;
import com.sisco_e.escola.model.repository.RefreshTokenRepository;
import com.sisco_e.escola.service.JwtService;
import com.sisco_e.escola.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.security.jwt.refresh-expiration}")
    private long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(UserDetails userDetails) {
        /**Remove tokens antigos do usuário antes de criar um novo
         * (opcional, dependendo da política de múltiplas sessões)
         */
        refreshTokenRepository.deleteByEmail(userDetails.getUsername());

        RefreshToken refreshToken = RefreshToken.builder()
                .email(userDetails.getUsername())
                .token(jwtService.generateRefreshToken(userDetails))
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        refreshTokenRepository.deleteByEmail(email);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteById(token);
    }
}
