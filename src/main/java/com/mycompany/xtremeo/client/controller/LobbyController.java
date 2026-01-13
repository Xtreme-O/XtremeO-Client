package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.controller.lobby.LobbyChatController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyHeaderController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyPlayerListController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyProfileController;
import com.mycompany.xtremeo.client.protocol.handler.lobby.LobbyResponseHandler;
import com.mycompany.xtremeo.client.service.lobby.LobbyService;
import com.mycompany.xtremeo.client.ui.dialog.HistoryDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class LobbyController {

    @FXML
    private StackPane lobbyRoot;
    @FXML
    private LobbyHeaderController headerController;
    @FXML
    private LobbyProfileController profileCardController;
    @FXML
    private LobbyPlayerListController playerListController;
    @FXML
    private LobbyChatController chatPanelController;

    @FXML
    public void initialize() {
        LobbyService.getInstance().sendLoadLobbyRequest();
        setupHeaderCallbacks();
        setupPlayerListCallbacks();
        loadLobbyData();
    }

    private void loadLobbyData() {
        LobbyResponseHandler.setOnLobbyLoad(body -> {
            Platform.runLater(() -> {
                System.out.println("Active Players :)");
                body.activeUsers().forEach(System.out::println);

                System.out.println("Top Players :)");
                body.playersScores().forEach(System.out::println);
            });
        });
    }

    private void setupHeaderCallbacks() {
        if (headerController != null) {
            headerController.setOnHistoryClick(this::showHistoryDialog);
        }
    }

    private void setupPlayerListCallbacks() {

    }

    private void showHistoryDialog() {
        HistoryDialog.show(lobbyRoot);
    }
}
