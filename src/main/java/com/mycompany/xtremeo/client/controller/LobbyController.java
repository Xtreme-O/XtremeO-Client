package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.controller.lobby.LobbyChatController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyHeaderController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyPlayerListController;
import com.mycompany.xtremeo.client.controller.lobby.LobbyProfileController;
import com.mycompany.xtremeo.client.ui.dialog.HistoryDialog;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class LobbyController {

    @FXML private StackPane lobbyRoot;
    @FXML private LobbyHeaderController headerController;
    @FXML private LobbyProfileController profileCardController;
    @FXML private LobbyPlayerListController playerListController;
    @FXML private LobbyChatController chatPanelController;

    @FXML
    public void initialize() {
        setupHeaderCallbacks();
        setupPlayerListCallbacks();
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
