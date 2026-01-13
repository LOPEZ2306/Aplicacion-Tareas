package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.Task;
import com.hexagonal.tasks.domain.ports.in.RetrieveTaskUseCase;
import com.hexagonal.tasks.domain.ports.out.TaskRepositoryPort;

import java.util.List;
import java.util.Optional;

public class RetrieveTaskUseCaseImpl implements RetrieveTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    public RetrieveTaskUseCaseImpl(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public Optional<Task> getTask(Long id, Long userId) {
        return taskRepositoryPort.findById(id)
                .filter(task -> task.getUserId() == null || task.getUserId().equals(userId));
    }

    @Override
    public List<Task> getAllTasksForUser(Long userId) {
        return taskRepositoryPort.findAllByUserId(userId);
    }

}
