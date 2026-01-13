package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.ports.in.DeleteUserUseCase;
import com.hexagonal.tasks.domain.ports.out.UserRepositoryPort;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public DeleteUserUseCaseImpl(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public boolean deleteUser(Long id) {
        return userRepositoryPort.deleteById(id);
    }
}
