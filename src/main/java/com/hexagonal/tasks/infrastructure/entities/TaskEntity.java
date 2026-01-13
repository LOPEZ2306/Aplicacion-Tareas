package com.hexagonal.tasks.infrastructure.entities;

import com.hexagonal.tasks.domain.models.Task;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private LocalDateTime creationdate;
    private boolean completed;
    private boolean active;
    private Long userId;

    public TaskEntity() {

    }

    public TaskEntity(Long id, String title, String description, LocalDateTime creationdate, boolean completed,
            boolean active, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationdate = creationdate;
        this.completed = completed;
        this.active = active;
        this.userId = userId;
    }

    // Se puede sustituir por un mapper

    public static TaskEntity fromDomainModel(Task task) {
        LocalDateTime date = null;
        if (task.getCreationDate() != null) {
            try {
                date = LocalDate.parse(task.getCreationDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        .atStartOfDay();
            } catch (Exception e) {
                // If it's already a full ISO string or other format, fallback or keep null
            }
        }
        return new TaskEntity(task.getId(), task.getTitle(), task.getDescription(),
                date,
                task.isCompleted(), task.isActive(), task.getUserId());
    }

    public Task todomainModel() {
        String dateStr = null;
        if (creationdate != null) {
            dateStr = creationdate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        return new Task(id, title, description,
                dateStr,
                completed, active, userId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(LocalDateTime creationdate) {
        this.creationdate = creationdate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}