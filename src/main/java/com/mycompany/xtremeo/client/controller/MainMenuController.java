/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.protocol.handler.auth.LoginResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.auth.LogoutResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.auth.RegisterResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.common.ErrorResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.game.*;
import com.mycompany.xtremeo.client.protocol.handler.message.GlobalMessageHandler;
import com.mycompany.xtremeo.client.protocol.handler.message.InGameMessageHandler;
import com.mycompany.xtremeo.client.service.audio.AudioService;
import com.mycompany.xtremeo.client.ui.dialog.*;
import com.mycompany.xtremeo.client.util.Screen;

import com.mycompany.xtremeo.client.ui.ComponentFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Elsobky
 */

public class MainMenuController {

    @FXML private StackPane mainRoot;
    @FXML private Button btnCpu;
    @FXML private Button btnMultiplayer;
    @FXML private Button btnHistory;
    @FXML private Button btnSoundToggle;
    private final AudioService audioService = AudioService.getInstance();
        
    @FXML
    public void initialize() {
        audioService.startDefaultBackgroundMusic();   
        ComponentFactory.configureAudioToggleButton(btnSoundToggle, "icon-button-icon");
        // test all responses
        LoginResponseHandler.setOnLoginResponseConsumer((player) -> {
            System.out.println("Login : " + player.player().getUsername());
        });
        LogoutResponseHandler.setOnLogoutResponseConsumer(body -> {
            System.out.println("Logout : " + body.username());
        });
        RegisterResponseHandler.setOnRegisterResponseConsumer(player -> {
            System.out.println("Register : " + player.player().getUsername());
        });
        ErrorResponseHandler.setOnErrorResponse(error -> {
            Platform.runLater(() -> ErrorDialog.show("Server Error", error.message()));
            System.out.println("Error : " + error.message());
        });
        InviteResponseHandler.setOnInviteResponseConsumer(invite -> {
            System.out.println("Invite : " + invite.player1().getUsername() + " VS " + invite.player2().getUsername());
        });
        InviteDeclinedResponseHandler.setOnInviteDeclinedResponse(declined -> {
            System.out.println("Invite rejected by " + declined.receiverId());
        });
        GlobalMessageHandler.setOnMessageResponse(message -> {
            System.out.println("Global Message : " + message.message());
        });
        InGameMessageHandler.setOnMessageResponse(message -> {
            System.out.println("In Game Message : " + message.getMessage());
        });
        SessionMessageResponseHandler.setOnSessionMessageReceived(body -> {
            System.out.println("Game State : " + body.state() + " with this move : " + body.move());
        });
    }

    @FXML
    void handlePlayCPU(ActionEvent event) {
        DifficultyDialog.show(mainRoot, difficulty -> {
            RecordGameDialog.show(mainRoot, record -> {
                BoardController controller = Navigator.setRoot(Screen.BOARD.getName());
                if (controller != null) {
                    controller.init(GameMode.WITH_CPU, difficulty, record, null);
                }
            });
        });
    }

    @FXML
    void handlePlayWithFriend(ActionEvent event) {
        RecordGameDialog.show(mainRoot, record -> {
            BoardController controller = Navigator.setRoot(Screen.BOARD.getName());
            if (controller != null) {
                controller.init(GameMode.WITH_FRIEND, record, null);
            }
        });
    }
    
    
    @FXML
    void handleMultiplayer(ActionEvent event) {
        System.out.println("Starting Multiplayer...");
        // Navigate to Login/Register screen


        //Navigator.setRoot(Screen.REGISTER.getName());
        Navigator.setRoot(Screen.LOGIN.getName());

//        RotateTransition animation = showLoading();
//        Navigator.setRootAsync(Screen.LOBBY.getName(), e -> hideLoading(animation));
    }


    @FXML
    void handleHistory(ActionEvent event) {
        HistoryDialog.show(mainRoot);
    }
    
    @FXML
    void handleHelp(ActionEvent event) {
        HelpDialog.show(mainRoot);
    }
    
}
