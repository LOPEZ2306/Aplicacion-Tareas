package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.AuthRequest;
import com.hexagonal.tasks.domain.models.AuthResponse;
import com.hexagonal.tasks.domain.models.RefreshToken;
import com.hexagonal.tasks.domain.models.User;
import com.hexagonal.tasks.domain.ports.in.AuthenticateUserUseCase;
import com.hexagonal.tasks.domain.ports.out.PasswordEncoderPort;
import com.hexagonal.tasks.domain.ports.out.RefreshTokenRepositoryPort;
import com.hexagonal.tasks.domain.ports.out.UserRepositoryPort;
import com.hexagonal.tasks.infrastructure.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Authentication Use Case Implementation
 * Handles user authentication logic
 */
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;

    public AuthenticateUserUseCaseImpl(UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            JwtTokenProvider jwtTokenProvider,
            RefreshTokenRepositoryPort refreshTokenRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        // Find user by username
        Optional<User> userOptional = userRepositoryPort.findByUsername(authRequest.getUsername());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = userOptional.get();

        // Validate password
        if (!passwordEncoderPort.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // Delete any existing refresh token for this user
        refreshTokenRepositoryPort.deleteByUsername(user.getUsername());

        // Generate JWT access token
        String accessToken = jwtTokenProvider.generateToken(user.getUsername(), user.getRole().name());

        // Generate refresh token
        String refreshTokenString = jwtTokenProvider.generateRefreshToken(user.getUsername());

        // Calculate expiry date for refresh token
        Date expiryDate = jwtTokenProvider.extractExpiration(refreshTokenString);
        LocalDateTime expiryLocalDateTime = expiryDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Save refresh token to database
        RefreshToken refreshToken = new RefreshToken(null, refreshTokenString, user.getUsername(), expiryLocalDateTime);
        refreshTokenRepositoryPort.save(refreshToken);

        return new AuthResponse(accessToken, refreshTokenString, user.getUsername(), user.getRole().name());
    }
}
