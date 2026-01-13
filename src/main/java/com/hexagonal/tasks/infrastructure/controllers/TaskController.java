package com.hexagonal.tasks.infrastructure.controllers;

import com.hexagonal.tasks.application.services.TaskService;
import com.hexagonal.tasks.domain.models.AdditionalTaskInfo;
import com.hexagonal.tasks.domain.models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Long userId = getCurrentUserId();
            task.setUserId(userId);
            Task createTask = taskService.createTask(task);
            return new ResponseEntity<>(createTask, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if ("UNAUTHORIZED".equals(e.getMessage()))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            throw e;
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable Long taskId) {
        try {
            Long userId = getCurrentUserId();
            return taskService.getTask(taskId, userId).map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            if ("UNAUTHORIZED".equals(e.getMessage()))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        try {
            Long userId = getCurrentUserId();
            List<Task> tasks = taskService.getAllTasksForUser(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (RuntimeException e) {
            if ("UNAUTHORIZED".equals(e.getMessage()))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            throw e;
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody Task updateTask) {
        try {
            Long userId = getCurrentUserId();
            return taskService.updateTask(taskId, updateTask, userId)
                    .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            if ("ACCESS_DENIED".equals(e.getMessage())) {
                return new ResponseEntity<>("Access Denied: You do not own this task", HttpStatus.FORBIDDEN);
            }
            throw e;
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long taskId) {
        try {
            Long userId = getCurrentUserId();
            if (taskService.deleteTask(taskId, userId)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            if ("UNAUTHORIZED".equals(e.getMessage()))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            throw e;
        }
    }

    @GetMapping("/{taskId}/aditionalInfo")
    public ResponseEntity<AdditionalTaskInfo> getAdditionalTaskInfo(@PathVariable Long taskId) {
        Long userId = getCurrentUserId();
        AdditionalTaskInfo additionalTaskInfo = taskService.getAdditionalTaskInfo(taskId, userId);
        return new ResponseEntity<>(additionalTaskInfo, HttpStatus.OK);
    }

    private Long getCurrentUserId() {
        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();

        if (auth == null || auth.getCredentials() == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        Object credentials = auth.getCredentials();
        if (credentials instanceof Number) {
            return ((Number) credentials).longValue();
        }

        try {
            return Long.valueOf(credentials.toString());
        } catch (Exception e) {
            throw new RuntimeException("UNAUTHORIZED");
        }
    }

}
