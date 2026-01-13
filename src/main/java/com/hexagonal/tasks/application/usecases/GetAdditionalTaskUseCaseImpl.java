package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.AdditionalTaskInfo;
import com.hexagonal.tasks.domain.ports.in.GetAdditionalTaskUseCase;
import com.hexagonal.tasks.domain.ports.out.ExternalServicePort;

public class GetAdditionalTaskUseCaseImpl implements GetAdditionalTaskUseCase {

    private final ExternalServicePort externalServicePort;
    private final com.hexagonal.tasks.domain.ports.out.TaskRepositoryPort taskRepositoryPort;

    public GetAdditionalTaskUseCaseImpl(ExternalServicePort externalServicePort,
            com.hexagonal.tasks.domain.ports.out.TaskRepositoryPort taskRepositoryPort) {
        this.externalServicePort = externalServicePort;
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public AdditionalTaskInfo getAdditionalTaskInfo(Long id, Long userId) {
        return taskRepositoryPort.findById(id)
                .filter(task -> task.getUserId().equals(userId))
                .map(task -> externalServicePort.getAdditionalTaskInfo(id))
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));
    }
}
