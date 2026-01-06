package com.mycompany.xtremeo.client.service.auth.model;

public class RegisterBody {
    private String username;
    private String password;
    private String avatar;

    public RegisterBody(String username, String password,String avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }
}
