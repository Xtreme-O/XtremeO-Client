package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.controller.lobby.LobbyChatController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyHeaderController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyPlayerListController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyProfileController;
import com.mycompany.xtremeo.client.protocol.handler.lobby.LobbyResponseHandler;
import com.mycompany.xtremeo.client.service.lobby.LobbyService;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
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
                PlayerService.getInstance().loadData(body);
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
        String username = PlayerService.getInstance().getUsername();
        HistoryDialog.show(lobbyRoot, true, username);
    }
}
