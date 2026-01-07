package com.hexagonal.tasks.infrastructure.controllers;

import com.hexagonal.tasks.domain.models.AuthRequest;
import com.hexagonal.tasks.domain.models.AuthResponse;
import com.hexagonal.tasks.domain.models.RefreshTokenRequest;
import com.hexagonal.tasks.domain.ports.in.AuthenticateUserUseCase;
import com.hexagonal.tasks.domain.ports.in.RefreshTokenUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * Handles login and authentication endpoints
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase,
            RefreshTokenUseCase refreshTokenUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    /**
     * Login endpoint
     * POST /auth/login
     * Request body: { "username": "user", "password": "pass" }
     * Response: { "token": "jwt-token", "refreshToken": "refresh-token",
     * "username": "user", "role": "USER" }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse authResponse = authenticateUserUseCase.authenticate(authRequest);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed: " + e.getMessage());
        }
    }

    /**
     * Refresh token endpoint
     * POST /auth/refresh
     * Request body: { "refreshToken": "refresh-token-string" }
     * Response: { "token": "new-jwt-token", "refreshToken": "same-refresh-token",
     * "username": "user", "role": "USER" }
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            AuthResponse authResponse = refreshTokenUseCase.refreshToken(refreshTokenRequest);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token refresh failed: " + e.getMessage());
        }
    }
}
