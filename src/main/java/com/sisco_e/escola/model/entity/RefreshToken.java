package com.sisco_e.escola.model.entity;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RedisHash(value = "RefreshToken", timeToLive = 604800) // 7 dias em segundos
public class RefreshToken {

    @Id
    private String token;

    @Column(unique = true, nullable = false, length = 64)
    private String tokenHash;

    @Indexed
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private boolean revoked;

    public boolean isExpired() {
        return expiryDate.isBefore(Instant.now());
    }
}
