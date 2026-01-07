package com.hexagonal.tasks.infrastructure.repositories;

import com.hexagonal.tasks.infrastructure.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findByUsername(String username);

    void deleteByUsername(String username);

    void deleteByToken(String token);
}
