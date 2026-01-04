package com.hexagonal.tasks.domain.ports.in;

import com.hexagonal.tasks.domain.models.AdditionalTaskInfo;

public interface GetAdditionalTaskUseCase {

    AdditionalTaskInfo getAdditionalTaskInfo(Long id);

}
