/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.auth.RegisterRequestBody;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.auth.AuthService;
import com.mycompany.xtremeo.client.service.auth.AuthServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
/**
 *
 * @author LOQ
 */
public class RegisterController {
    
    @FXML private Image avatar1;
    @FXML private Image avatar2;
    @FXML private Image avatar3;
    @FXML private Image avatar4;
    @FXML private Image avatar5;
    @FXML 
    TextField username;
    @FXML 
    TextField password;
    @FXML 
    TextField confirmPassword;
    @FXML
    Button startGame;
    /*//////////////TODOS////////////////////////////////*/
    // Where to add the method that will check if pw == confPw ??
    String avatarUrl;
    public void onAvatarSelect(ActionEvent event){
        Image selectedAvatar = (Image) event.getSource();
        this.avatarUrl = selectedAvatar.getUrl();
    }
    
    public void onRegister(ActionEvent event){
        String username = this.username.getText();
        String password = this.password.getText();
        Header header = new Header("JSON", "REGISTER");
        RegisterRequestBody body = new RegisterRequestBody(username, password, avatarUrl);
        RequestEnvelope<RegisterRequestBody> request = new RequestEnvelope<>(header, body);
        AuthService authservice = new AuthServiceImpl(new ClientConnection());
        authservice.send(request);
    }
    
}
