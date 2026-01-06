package com.mycompany.xtremeo.client.service.auth.model;

public class AuthResponse {
    private boolean success;
    private String message;

    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
