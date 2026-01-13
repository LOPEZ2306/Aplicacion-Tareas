package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.User;
import com.hexagonal.tasks.domain.ports.in.UpdateUserUseCase;
import com.hexagonal.tasks.domain.ports.out.UserRepositoryPort;
import com.hexagonal.tasks.domain.ports.out.PasswordEncoderPort; // Assuming we might need to hash password on update?
import java.util.Optional;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UpdateUserUseCaseImpl(UserRepositoryPort userRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        if (userRepositoryPort.findById(id).isPresent()) {
            User userToUpdate = user;
            userToUpdate.setId(id);
            // If password is changed, it should probably be hashed, but let's check
            // CreateUserUseCaseImpl to see how they handle it.
            // For now, I will assume the incoming user might have a raw password if it's
            // being updated.
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userToUpdate.setPassword(passwordEncoderPort.encode(user.getPassword()));
            } else {
                // If no password provided, maybe keep the old one?
                // This logic is tricky without retrieving the old one first.
                // Let's retrieve the old one.
                User existingUser = userRepositoryPort.findById(id).get();
                userToUpdate.setPassword(existingUser.getPassword());
            }

            return Optional.of(userRepositoryPort.save(userToUpdate));
        }
        return Optional.empty();
    }
}
