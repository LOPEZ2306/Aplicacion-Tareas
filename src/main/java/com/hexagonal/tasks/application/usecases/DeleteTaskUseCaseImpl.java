package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.ports.in.DeleteTaskUseCase;
import com.hexagonal.tasks.domain.ports.out.TaskRepositoryPort;

public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    public DeleteTaskUseCaseImpl(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public boolean deleteTask(Long id, Long userId) {
        return taskRepositoryPort.findById(id)
                .filter(task -> task.getUserId() == null || task.getUserId().equals(userId))
                .map(task -> taskRepositoryPort.deleteById(id))
                .orElse(false);
    }
}