/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.protocol.handler.auth.RegisterResponseHandler;
import com.mycompany.xtremeo.client.service.auth.RegisterService;
import com.mycompany.xtremeo.client.ui.AvatarFactory;
import com.mycompany.xtremeo.client.ui.dialog.ErrorDialog;
import com.mycompany.xtremeo.client.util.Avatars;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.Arrays;

/**
 * @author LOQ
 */
public class RegisterController {

    @FXML
    private ListView<ImageView> avatarListView;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    TextField confirmPassword;
    @FXML
    Button startGame;
    String avatarUrl;

    @FXML
    public void initialize() {
        initListView();
        setUpAvatars();
        registerSuccess();
    }

    private static void registerSuccess() {
        RegisterResponseHandler.setOnRegisterResponseConsumer(p->{
            Platform.runLater(()->{
                Navigator.setRoot(Screen.LOBBY.getName());
            });
        });
    }

    public void onRegister(ActionEvent event) {
        String username = this.username.getText();
        String password = this.password.getText();
        if (!validatePassword(password)) {
            ErrorDialog.show("Registration error", "Passwords don't match !");
            return;
        }
        RegisterService service = RegisterService.getInstance();
        service.register(username, password, avatarUrl);
    }

    private boolean validatePassword(String password) {
        String confirmedPassword = this.confirmPassword.getText();
        return password.equals(confirmedPassword);
    }

    @FXML
    public void onBack(ActionEvent event) {
        Navigator.setRoot(Screen.MAIN.getName());
    }

    @FXML
    public void onLogin(ActionEvent event) {
        Navigator.setRoot(Screen.LOGIN.getName());
    }

    public void setUpAvatars() {
        var images = Arrays.stream(Avatars.URLS).map(url -> AvatarFactory.create(url, 48)).toList();
        avatarListView.getItems().clear();
        avatarListView.getItems().addAll(images);
    }

    public void initListView() {
        avatarListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item);
                }
            }
        });

        avatarListView.getSelectionModel()
                .selectedIndexProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null && newVal.intValue() >= 0 && newVal.intValue() < Avatars.URLS.length) {
                        avatarUrl = Avatars.URLS[newVal.intValue()];
                    }
                });
    }

}