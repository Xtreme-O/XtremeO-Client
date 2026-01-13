/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.protocol.handler.auth.LoginResponseHandler;
import com.mycompany.xtremeo.client.service.auth.LoginService;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author LOQ
 */
public class LoginUIController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;
    @FXML
    public void initialize() {
        loginSuccess();
    }

    private static void loginSuccess() {
        LoginResponseHandler.setOnLoginResponseConsumer(p->{
            Platform.runLater(()->{
                Navigator.setRoot(Screen.LOBBY.getName());
            });
        });
    }

    @FXML
    void onClick(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        LoginService service = LoginService.getInstance();
        service.login(username,password);

    }

    @FXML
    void onBack(ActionEvent e) {
        Navigator.setRoot(Screen.MAIN.getName());
    }

    @FXML
    void onCreateAccount(ActionEvent e) {
        Navigator.setRoot(Screen.REGISTER.getName());
    }

}