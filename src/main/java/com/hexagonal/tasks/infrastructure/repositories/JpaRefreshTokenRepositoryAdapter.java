package com.hexagonal.tasks.infrastructure.repositories;

import com.hexagonal.tasks.domain.models.RefreshToken;
import com.hexagonal.tasks.domain.ports.out.RefreshTokenRepositoryPort;
import com.hexagonal.tasks.infrastructure.entities.RefreshTokenEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class JpaRefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {

    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;

    public JpaRefreshTokenRepositoryAdapter(JpaRefreshTokenRepository jpaRefreshTokenRepository) {
        this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity entity = RefreshTokenEntity.fromDomainModel(refreshToken);
        RefreshTokenEntity savedEntity = jpaRefreshTokenRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRefreshTokenRepository.findByToken(token)
                .map(RefreshTokenEntity::toDomainModel);
    }

    @Override
    public Optional<RefreshToken> findByUsername(String username) {
        return jpaRefreshTokenRepository.findByUsername(username)
                .map(RefreshTokenEntity::toDomainModel);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        jpaRefreshTokenRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        jpaRefreshTokenRepository.deleteByToken(token);
    }
}
