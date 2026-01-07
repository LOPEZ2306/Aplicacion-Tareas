package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.User;
import com.hexagonal.tasks.domain.ports.in.CreateUserUseCase;
import com.hexagonal.tasks.domain.ports.out.UserRepositoryPort;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public CreateUserUseCaseImpl(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User createUser(User user) {
        return userRepositoryPort.save(user);
    }
}
