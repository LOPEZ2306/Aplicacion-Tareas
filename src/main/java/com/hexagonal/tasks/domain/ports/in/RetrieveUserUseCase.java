package com.hexagonal.tasks.domain.ports.in;

import com.hexagonal.tasks.domain.models.User;
import java.util.Optional;
import java.util.List;

public interface RetrieveUserUseCase {
    Optional<User> getUser(Long id);

    List<User> getAllUsers();
}
