package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.User;
import com.hexagonal.tasks.domain.ports.in.CreateUserUseCase;
import com.hexagonal.tasks.domain.ports.out.PasswordEncoderPort;
import com.hexagonal.tasks.domain.ports.out.UserRepositoryPort;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public CreateUserUseCaseImpl(UserRepositoryPort userRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));
        return userRepositoryPort.save(user);
    }
}
