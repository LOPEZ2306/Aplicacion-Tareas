package com.hexagonal.tasks.domain.ports.in;

import com.hexagonal.tasks.domain.models.Task;

import java.util.Optional;

public interface UpdateTaskUseCase {

    Optional<Task> updateTask(long id, Task updateTask);

}
