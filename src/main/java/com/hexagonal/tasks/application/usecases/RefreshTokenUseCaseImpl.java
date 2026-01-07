package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.AuthResponse;
import com.hexagonal.tasks.domain.models.RefreshToken;
import com.hexagonal.tasks.domain.models.RefreshTokenRequest;
import com.hexagonal.tasks.domain.models.User;
import com.hexagonal.tasks.domain.ports.in.RefreshTokenUseCase;
import com.hexagonal.tasks.domain.ports.out.RefreshTokenRepositoryPort;
import com.hexagonal.tasks.domain.ports.out.UserRepositoryPort;
import com.hexagonal.tasks.infrastructure.security.JwtTokenProvider;

import java.util.Optional;

/**
 * Refresh Token Use Case Implementation
 * Handles refresh token validation and new access token generation
 */
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final JwtTokenProvider jwtTokenProvider;

    public RefreshTokenUseCaseImpl(RefreshTokenRepositoryPort refreshTokenRepositoryPort,
            UserRepositoryPort userRepositoryPort,
            JwtTokenProvider jwtTokenProvider) {
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        // Find refresh token in database
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepositoryPort.findByToken(requestRefreshToken);

        if (refreshTokenOptional.isEmpty()) {
            throw new RuntimeException("Refresh token not found");
        }

        RefreshToken refreshToken = refreshTokenOptional.get();

        // Verify token is not expired
        if (refreshToken.isExpired()) {
            refreshTokenRepositoryPort.deleteByToken(requestRefreshToken);
            throw new RuntimeException("Refresh token has expired. Please login again");
        }

        // Validate the JWT token itself
        if (!jwtTokenProvider.validateToken(requestRefreshToken)) {
            refreshTokenRepositoryPort.deleteByToken(requestRefreshToken);
            throw new RuntimeException("Invalid refresh token");
        }

        // Get user information
        String username = refreshToken.getUsername();
        Optional<User> userOptional = userRepositoryPort.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        // Generate new access token
        String newAccessToken = jwtTokenProvider.generateToken(user.getUsername(), user.getRole().name());

        // Return response with new access token and same refresh token
        return new AuthResponse(newAccessToken, requestRefreshToken, user.getUsername(), user.getRole().name());
    }
}
