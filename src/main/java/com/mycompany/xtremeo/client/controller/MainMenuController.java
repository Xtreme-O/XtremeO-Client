/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.protocol.handler.auth.LoginResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.auth.LogoutResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.auth.RegisterResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.common.ErrorResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.game.*;
import com.mycompany.xtremeo.client.protocol.handler.message.GlobalMessageHandler;
import com.mycompany.xtremeo.client.protocol.handler.message.InGameMessageHandler;
import com.mycompany.xtremeo.client.ui.dialog.DifficultyDialog;
import com.mycompany.xtremeo.client.util.Screen;

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

    @FXML
    private StackPane mainRoot;
    @FXML
    private Button btnCpu;
    @FXML
    private Button btnMultiplayer;

    public static Difficulty selectedDifficulty = Difficulty.HARD;

    @FXML
    public void initialize() {
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
            System.out.println("Starting Single Player with " + difficulty + " difficulty...");
            selectedDifficulty = difficulty;
            BoardController.selectedMode = GameMode.WITH_CPU;
            Navigator.setRoot(Screen.BOARD.getName());
        });
    }

    @FXML
    void handlePlayWithFreind(ActionEvent event) {
        System.out.println("Starting Play With Freind...");
        BoardController.selectedMode = GameMode.WITH_FRIEND;
        Navigator.setRoot(Screen.BOARD.getName());
    }


    @FXML
    void handleMultiplayer(ActionEvent event) {
        System.out.println("Starting Multiplayer...");
        // Navigate to Login/Register screen
    }
}
