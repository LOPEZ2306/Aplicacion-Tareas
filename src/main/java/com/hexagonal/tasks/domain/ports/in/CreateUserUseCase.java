package com.hexagonal.tasks.domain.ports.in;

import com.hexagonal.tasks.domain.models.User;

public interface CreateUserUseCase {
    User createUser(User user);
}
