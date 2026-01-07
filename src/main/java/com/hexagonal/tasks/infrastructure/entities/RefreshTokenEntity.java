package com.hexagonal.tasks.infrastructure.entities;

import com.hexagonal.tasks.domain.models.RefreshToken;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public RefreshTokenEntity() {
    }

    public RefreshTokenEntity(Long id, String token, String username, LocalDateTime expiryDate) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.expiryDate = expiryDate;
    }

    public static RefreshTokenEntity fromDomainModel(RefreshToken refreshToken) {
        return new RefreshTokenEntity(
                refreshToken.getId(),
                refreshToken.getToken(),
                refreshToken.getUsername(),
                refreshToken.getExpiryDate());
    }

    public RefreshToken toDomainModel() {
        return new RefreshToken(id, token, username, expiryDate);
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
}
