package com.hexagonal.tasks.domain.models;

import java.time.LocalDateTime;

/**
 * Refresh Token Domain Model
 */
public class RefreshToken {
    private Long id;
    private String token;
    private String username;
    private LocalDateTime expiryDate;

    public RefreshToken() {
    }

    public RefreshToken(Long id, String token, String username, LocalDateTime expiryDate) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }
}
