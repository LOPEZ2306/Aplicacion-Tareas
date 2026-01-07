package com.hexagonal.tasks.domain.ports.in;

import com.hexagonal.tasks.domain.models.AuthResponse;
import com.hexagonal.tasks.domain.models.RefreshTokenRequest;

public interface RefreshTokenUseCase {
    AuthResponse refreshToken(RefreshTokenRequest request);
}
