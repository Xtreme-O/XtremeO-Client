package com.mycompany.xtremeo.client.service.auth.model;

public class LoginBody {
    private String username;
    private String password;

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
