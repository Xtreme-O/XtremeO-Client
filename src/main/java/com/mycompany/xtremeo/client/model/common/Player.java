package com.mycompany.xtremeo.client.model.common;

import com.mycompany.xtremeo.client.enums.PlayerStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Player {

    int id;
    String username;
    String avatarUrl;
    PlayerStatus status;
    LocalDateTime createdAt;
    LocalDateTime lastLogin;

    public Player(int id, String username, String avatarUrl, PlayerStatus status, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" + "id=" + id + ", username=" + username + ", avatarUrl=" + avatarUrl + ", status=" + status + ", createdAt=" + createdAt + ", lastLogin=" + lastLogin + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.username, other.username);
    }
    
    
}
