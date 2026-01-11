/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.auth.LoginRequestBody;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.auth.AuthService;
import com.mycompany.xtremeo.client.service.auth.AuthServiceImpl;
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
    void onClick(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Header header = new Header("JSON", "LOGIN");
        LoginRequestBody body = new LoginRequestBody(username, password);

        RequestEnvelope<LoginRequestBody> req = new RequestEnvelope<>(header, body);

        AuthService authservice = new AuthServiceImpl(new ClientConnection());
        authservice.send(req);
    }

}
