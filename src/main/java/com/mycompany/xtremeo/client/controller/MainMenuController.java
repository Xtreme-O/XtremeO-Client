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
import com.mycompany.xtremeo.client.ui.dialog.DifficultyDialog;
import com.mycompany.xtremeo.client.ui.dialog.HelpDialog;
import com.mycompany.xtremeo.client.ui.dialog.HistoryDialog;
import com.mycompany.xtremeo.client.ui.dialog.RecordGameDialog;
import com.mycompany.xtremeo.client.util.Screen;

import com.mycompany.xtremeo.client.ui.ComponentFactory;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

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
            System.out.println("Login : " + player.getUsername());
        });
        LogoutResponseHandler.setOnLogoutResponseConsumer(body -> {
            System.out.println("Logout : " + body.username());
        });
        RegisterResponseHandler.setOnRegisterResponseConsumer(player -> {
            System.out.println("Register : " + player.getUsername());
        });
        ErrorResponseHandler.setOnErrorResponse(error -> {
            System.out.println("Error : " + error.message());
        });
        InviteResponseHandler.setOnInviteResponseConsumer(invite -> {
            System.out.println("Invite : " + invite.player1().getUsername() + " VS " + invite.player2().getUsername());
        });
        InviteConfirmResponseHandler.setOnInviteConfirmResponse(confirm -> {
            System.out.println("Confirm from " + confirm.receiverId() + " to " + confirm.senderId());
        });
        InviteDeclinedResponseHandler.setOnInviteDeclinedResponse(declined -> {
            System.out.println("Invite rejected by " + declined.receiverId());
        });
        GlobalMessageHandler.setOnMessageResponse(message -> {
            System.out.println("Global Message : " + message.getMessage());
        });
        InGameMessageHandler.setOnMessageResponse(message -> {
            System.out.println("In Game Message : " + message.getMessage());
        });
        SessionMessageResponseHandler.setOnSessionMessageReceived(body -> {
            System.out.println("Game State : " + body.state() + " with this move : " + body.move());
        });
        PartnerDisconnectedResponseHandler.setOnPartnerDisconnected(body -> {
            System.out.println("Player " + body.playerId() + " Disconnected");
        });
    }

    @FXML
    void handlePlayCPU(ActionEvent event) {
        DifficultyDialog.show(mainRoot, difficulty -> {
            RecordGameDialog.show(mainRoot, record -> {
                BoardController controller = Navigator.setRoot(Screen.BOARD.getName());
                if (controller != null) {
                    controller.init(GameMode.WITH_CPU, difficulty, record);
                }
            });
        });
    }

    @FXML
    void handlePlayWithFriend(ActionEvent event) {
        RecordGameDialog.show(mainRoot, record -> {
            BoardController controller = Navigator.setRoot(Screen.BOARD.getName());
            if (controller != null) {
                controller.init(GameMode.WITH_FRIEND, record);
            }
        });
    }
    
    
    @FXML
    void handleMultiplayer(ActionEvent event) {
        RotateTransition animation = showLoading();
        Navigator.setRootAsync(Screen.LOBBY.getName(), e -> hideLoading(animation));
    }

    private RotateTransition showLoading() {
        btnMultiplayer.setDisable(true);
        btnMultiplayer.getStyleClass().add("loading");
        btnMultiplayer.setText("CONNECTING...");

        Arc spinner = ComponentFactory.createSpinner(16, Color.BLACK);
        btnMultiplayer.setGraphic(spinner);

        RotateTransition rotate = ComponentFactory.createSpinAnimation(spinner);
        rotate.play();
        return rotate;
    }

    private void hideLoading(RotateTransition animation) {
        animation.stop();
        btnMultiplayer.setGraphic(null);
        btnMultiplayer.setText("Multiplayer");
        btnMultiplayer.getStyleClass().remove("loading");
        btnMultiplayer.setDisable(false);
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
