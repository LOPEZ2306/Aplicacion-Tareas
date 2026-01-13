package com.hexagonal.tasks.domain.ports.in;

import com.hexagonal.tasks.domain.models.User;
import java.util.Optional;

public interface UpdateUserUseCase {
    Optional<User> updateUser(Long id, User user);
}
