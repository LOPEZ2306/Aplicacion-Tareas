package com.hexagonal.tasks.infrastructure.repositories;

import com.hexagonal.tasks.infrastructure.entities.TaskEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskRepository extends org.springframework.data.jpa.repository.JpaRepository <TaskEntity, Long>{
}
