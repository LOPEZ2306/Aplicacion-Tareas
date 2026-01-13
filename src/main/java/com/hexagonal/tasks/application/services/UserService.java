package com.hexagonal.tasks.application.services;

import com.hexagonal.tasks.domain.models.User;
import com.hexagonal.tasks.domain.ports.in.CreateUserUseCase;
import com.hexagonal.tasks.domain.ports.in.DeleteUserUseCase;
import com.hexagonal.tasks.domain.ports.in.RetrieveUserUseCase;
import com.hexagonal.tasks.domain.ports.in.UpdateUserUseCase;

import java.util.List;
import java.util.Optional;

public class UserService implements CreateUserUseCase, RetrieveUserUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final CreateUserUseCase createUserUseCase;
    private final RetrieveUserUseCase retrieveUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserService(CreateUserUseCase createUserUseCase, RetrieveUserUseCase retrieveUserUseCase,
            UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.retrieveUserUseCase = retrieveUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
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

    @Override
    public Optional<User> updateUser(Long id, User user) {
        return updateUserUseCase.updateUser(id, user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return deleteUserUseCase.deleteUser(id);
    }
}
