package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.Task;
import com.hexagonal.tasks.domain.ports.in.UpdateTaskUseCase;
import com.hexagonal.tasks.domain.ports.out.TaskRepositoryPort;

import java.util.Optional;

public class UpdateTaskUseCaseImpl implements UpdateTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    public UpdateTaskUseCaseImpl(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public Optional<Task> updateTask(long id, Task updateTask, Long userId) {
        Optional<Task> existingTaskOptional = taskRepositoryPort.findById(id);

        if (existingTaskOptional.isEmpty()) {
            return Optional.empty();
        }

        Task existingTask = existingTaskOptional.get();

        // Ownership verification (allow if legacy task has null userId or matches
        // current user)
        if (existingTask.getUserId() != null && !existingTask.getUserId().equals(userId)) {
            // Log for debugging
            System.err.println("Access Denied for Task ID: " + id + ". Task owner: " + existingTask.getUserId()
                    + ", Current user: " + userId);
            throw new RuntimeException("ACCESS_DENIED");
        }

        updateTask.setId(id);
        updateTask.setUserId(userId);

        // Preserve creation date if missing
        if (updateTask.getCreationDate() == null) {
            updateTask.setCreationDate(existingTask.getCreationDate());
        }

        return taskRepositoryPort.update(updateTask);
    }
}
