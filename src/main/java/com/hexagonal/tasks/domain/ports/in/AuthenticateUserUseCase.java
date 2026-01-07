package com.hexagonal.tasks.domain.ports.in;

import com.hexagonal.tasks.domain.models.AuthRequest;
import com.hexagonal.tasks.domain.models.AuthResponse;

public interface AuthenticateUserUseCase {
    AuthResponse authenticate(AuthRequest authRequest);
}
