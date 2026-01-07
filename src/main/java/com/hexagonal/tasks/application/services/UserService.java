package com.hexagonal.tasks.application.services;

import com.hexagonal.tasks.domain.models.User;
import com.hexagonal.tasks.domain.ports.in.CreateUserUseCase;
import com.hexagonal.tasks.domain.ports.in.RetrieveUserUseCase;

import java.util.List;
import java.util.Optional;

public class UserService implements CreateUserUseCase, RetrieveUserUseCase {

    private final CreateUserUseCase createUserUseCase;
    private final RetrieveUserUseCase retrieveUserUseCase;

    public UserService(CreateUserUseCase createUserUseCase, RetrieveUserUseCase retrieveUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.retrieveUserUseCase = retrieveUserUseCase;
    }

    @Override
    public User createUser(User user) {
        return createUserUseCase.createUser(user);
    }

    @Override
    public Optional<User> getUser(Long id) {
        return retrieveUserUseCase.getUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return retrieveUserUseCase.getAllUsers();
    }
}
