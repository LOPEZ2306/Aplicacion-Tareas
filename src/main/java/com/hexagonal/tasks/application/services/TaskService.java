package com.hexagonal.tasks.application.services;

import com.hexagonal.tasks.domain.models.AdditionalTaskInfo;
import com.hexagonal.tasks.domain.models.Task;
import com.hexagonal.tasks.domain.ports.in.CreateTaskUseCase;
import com.hexagonal.tasks.domain.ports.in.DeleteTaskUseCase;
import com.hexagonal.tasks.domain.ports.in.GetAdditionalTaskUseCase;
import com.hexagonal.tasks.domain.ports.in.RetrieveTaskUseCase;
import com.hexagonal.tasks.domain.ports.in.UpdateTaskUseCase;

import java.util.List;
import java.util.Optional;

public class TaskService implements CreateTaskUseCase, RetrieveTaskUseCase, UpdateTaskUseCase, DeleteTaskUseCase,
        GetAdditionalTaskUseCase {

    private final CreateTaskUseCase createTaskUseCase;
    private final RetrieveTaskUseCase retrieveTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetAdditionalTaskUseCase getAdditionalTaskUseCase;

    public TaskService(CreateTaskUseCase createTaskUseCase, RetrieveTaskUseCase retrieveTaskUseCase,
            UpdateTaskUseCase updateTaskUseCase, DeleteTaskUseCase deleteTaskUseCase,
            GetAdditionalTaskUseCase getAdditionalTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.retrieveTaskUseCase = retrieveTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.getAdditionalTaskUseCase = getAdditionalTaskUseCase;
    }

    @Override
    public Task createTask(Task task) {
        return createTaskUseCase.createTask(task);
    }

    @Override
    public boolean deleteTask(Long id, Long userId) {
        return deleteTaskUseCase.deleteTask(id, userId);
    }

    @Override
    public AdditionalTaskInfo getAdditionalTaskInfo(Long id, Long userId) {
        return getAdditionalTaskUseCase.getAdditionalTaskInfo(id, userId);
    }

    @Override
    public Optional<Task> getTask(Long id, Long userId) {
        return retrieveTaskUseCase.getTask(id, userId);
    }

    @Override
    public List<Task> getAllTasksForUser(Long userId) {
        return retrieveTaskUseCase.getAllTasksForUser(userId);
    }

    @Override
    public Optional<Task> updateTask(long id, Task updateTask, Long userId) {
        return updateTaskUseCase.updateTask(id, updateTask, userId);
    }
}
