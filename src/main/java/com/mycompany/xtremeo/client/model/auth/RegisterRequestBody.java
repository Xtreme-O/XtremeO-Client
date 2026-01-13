package com.mycompany.xtremeo.client.model.auth;

import javafx.scene.layout.StackPane;

public class RegisterRequestBody {
    private String username;
    private String password;
    private String avatar; //MODIFIED THE AVATAR FROMS STRING TO STACKPANE

    public RegisterRequestBody(String username, String password, String avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }
}
