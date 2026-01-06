package com.hexagonal.tasks.infrastructure.repositories;

import com.hexagonal.tasks.infrastructure.entities.TaskEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTaskRepository extends org.springframework.data.jpa.repository.JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByActiveTrue();
}
