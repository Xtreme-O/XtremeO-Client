package com.mycompany.xtremeo.client.model.auth;

public class RegisterRequestBody {
    private String username;
    private String password;
    private String avatar;

    public RegisterRequestBody(String username, String password, String avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }
}
