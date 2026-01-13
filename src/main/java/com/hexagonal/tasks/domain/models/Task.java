package com.hexagonal.tasks.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("creationDate")
    private String creationDate; // Using String for maximum reliability

    @JsonProperty("completed")
    private boolean completed;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("userId")
    private Long userId;

    public Task() {
        this.active = true;
    }

    public Task(Long id, String title, String description, String creationDate, boolean completed, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.completed = completed;
        this.active = true;
        this.userId = userId;
    }

    public Task(Long id, String title, String description, String creationDate, boolean completed,
            boolean active, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.completed = completed;
        this.active = active;
        this.userId = userId;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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
