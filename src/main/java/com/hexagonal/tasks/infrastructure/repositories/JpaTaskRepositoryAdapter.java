package com.hexagonal.tasks.infrastructure.repositories;

import com.hexagonal.tasks.domain.models.Task;
import com.hexagonal.tasks.domain.ports.out.TaskRepositoryPort;
import com.hexagonal.tasks.infrastructure.entities.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaTaskRepositoryAdapter implements TaskRepositoryPort {

    private final JpaTaskRepository jpaTaskRepository;

    public JpaTaskRepositoryAdapter(JpaTaskRepository jpaTaskRepository) {
        this.jpaTaskRepository = jpaTaskRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity taskEntity = TaskEntity.fromDomainModel(task);
        TaskEntity savedTaskEntity = jpaTaskRepository.save(taskEntity);
        return savedTaskEntity.todomainModel();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository.findById(id)
                .filter(TaskEntity::isActive)
                .map(TaskEntity::todomainModel);
    }

    @Override
    public List<Task> findAll() {
        return jpaTaskRepository.findByActiveTrue().stream()
                .map(TaskEntity::todomainModel)
                .toList();
    }

    @Override
    public Optional<Task> update(Task task) {
        if (jpaTaskRepository.existsById(task.getId())) {
            // We ensure we are not updating a deleted task implicitly,
            // although usually the use case retrieves it first.
            // But to be safe and consistent with Hexagonal, we just save what comes from
            // domain.
            // If domain says active=true, it stays active.
            // However, strictly speaking, if it was soft deleted, we might want to prevent
            // updates.
            // For now, simple save.
            TaskEntity taskEntity = TaskEntity.fromDomainModel(task);
            TaskEntity savedTaskEntity = jpaTaskRepository.save(taskEntity);
            return Optional.of(savedTaskEntity.todomainModel());
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return jpaTaskRepository.findById(id)
                .filter(TaskEntity::isActive)
                .map(entity -> {
                    entity.setActive(false);
                    jpaTaskRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }
}
