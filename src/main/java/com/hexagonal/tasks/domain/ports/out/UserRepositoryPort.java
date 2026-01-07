package com.hexagonal.tasks.domain.ports.out;

import com.hexagonal.tasks.domain.models.User;
import java.util.Optional;
import java.util.List;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    boolean deleteById(Long id);
}
