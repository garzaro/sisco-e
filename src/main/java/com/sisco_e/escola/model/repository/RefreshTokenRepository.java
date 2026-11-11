package com.sisco_e.escola.model.repository;

import com.sisco_e.escola.model.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Transactional
    void deleteByUsername(String username);

    @Transactional
    @Modifying
    @Query("update RefreshToken t set " +
            "t.revoked = true where " +
            "t.username = :username and " +
            "t.revoked = false")
    int revokeAllByUsername(String username);
}
