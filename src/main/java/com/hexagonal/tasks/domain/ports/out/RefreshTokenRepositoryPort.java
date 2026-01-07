package com.hexagonal.tasks.domain.ports.out;

import com.hexagonal.tasks.domain.models.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepositoryPort {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUsername(String username);

    void deleteByUsername(String username);

    void deleteByToken(String token);
}
