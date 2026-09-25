package com.sisco_e.escola.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "RefreshToken", timeToLive = 604800) // 7 dias em segundos
public class RefreshToken {

    @Id
    private String token;

    @Indexed
    private String email;

    private Instant expiryDate;
}
