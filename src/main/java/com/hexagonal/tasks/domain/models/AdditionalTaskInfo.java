package com.hexagonal.tasks.domain.models;

public class AdditionalTaskInfo {

    private final Long userId;
    private final String userName;
    private final String userEmail;

    public AdditionalTaskInfo(Long userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public Long getUserId() {
        return userId;
    }
}
