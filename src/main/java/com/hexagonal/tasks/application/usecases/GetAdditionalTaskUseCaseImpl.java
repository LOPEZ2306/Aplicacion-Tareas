package com.hexagonal.tasks.application.usecases;

import com.hexagonal.tasks.domain.models.AdditionalTaskInfo;
import com.hexagonal.tasks.domain.ports.in.GetAdditionalTaskUseCase;
import com.hexagonal.tasks.domain.ports.out.ExternalServicePort;

public class GetAdditionalTaskUseCaseImpl implements GetAdditionalTaskUseCase {

    private final ExternalServicePort externalServicePort;

    public GetAdditionalTaskUseCaseImpl(ExternalServicePort externalServicePort) {
        this.externalServicePort = externalServicePort;
    }

    @Override
    public AdditionalTaskInfo getAdditionalTaskInfo(Long id) {
        return externalServicePort.getAdditionalTaskInfo(id);
    }
}
