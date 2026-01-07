package com.hexagonal.tasks.infrastructure.config;

import com.hexagonal.tasks.application.services.TaskService;
import com.hexagonal.tasks.application.usecases.CreateTaskUseCaseImpl;
import com.hexagonal.tasks.application.usecases.DeleteTaskUseCaseImpl;
import com.hexagonal.tasks.application.usecases.GetAdditionalTaskUseCaseImpl;
import com.hexagonal.tasks.application.usecases.RetrieveTaskUseCaseImpl;
import com.hexagonal.tasks.application.usecases.UpdateTaskUseCaseImpl;
import com.hexagonal.tasks.domain.ports.in.GetAdditionalTaskUseCase;
import com.hexagonal.tasks.domain.ports.out.ExternalServicePort;
import com.hexagonal.tasks.domain.ports.out.TaskRepositoryPort;
import com.hexagonal.tasks.infrastructure.adapters.ExternalServiceAdapter;
import com.hexagonal.tasks.infrastructure.repositories.JpaTaskRepository;
import com.hexagonal.tasks.infrastructure.repositories.JpaTaskRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public TaskService taskService(TaskRepositoryPort taskRepositoryPort,
            GetAdditionalTaskUseCase getAdditionalTaskUseCase) {
        return new TaskService(
                new CreateTaskUseCaseImpl(taskRepositoryPort),
                new RetrieveTaskUseCaseImpl(taskRepositoryPort),
                new UpdateTaskUseCaseImpl(taskRepositoryPort),
                new DeleteTaskUseCaseImpl(taskRepositoryPort),
                getAdditionalTaskUseCase);
    }

    @Bean
    public TaskRepositoryPort taskRepositoryPort(JpaTaskRepositoryAdapter jpaTaskRepositoryAdapter) {
        return jpaTaskRepositoryAdapter;
    }

    @Bean
    public GetAdditionalTaskUseCase getAdditionalTaskUseCase(ExternalServicePort externalServicePort) {
        return new GetAdditionalTaskUseCaseImpl(externalServicePort);
    }

    @Bean
    public ExternalServiceAdapter externalServiceAdapter() {
        return new ExternalServiceAdapter();
    }

    @Bean
    public com.hexagonal.tasks.application.services.UserService userService(
            com.hexagonal.tasks.domain.ports.out.UserRepositoryPort userRepositoryPort) {
        return new com.hexagonal.tasks.application.services.UserService(
                new com.hexagonal.tasks.application.usecases.CreateUserUseCaseImpl(userRepositoryPort),
                new com.hexagonal.tasks.application.usecases.RetrieveUserUseCaseImpl(userRepositoryPort));
    }

    @Bean
    public com.hexagonal.tasks.domain.ports.out.UserRepositoryPort userRepositoryPort(
            com.hexagonal.tasks.infrastructure.repositories.JpaUserRepositoryAdapter jpaUserRepositoryAdapter) {
        return jpaUserRepositoryAdapter;
    }

}
