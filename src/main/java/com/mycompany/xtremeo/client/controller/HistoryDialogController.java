package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.game.GameHistoryEntry;
import com.mycompany.xtremeo.client.service.recording.GameFileService;
import com.mycompany.xtremeo.client.service.recording.JsonFileHandler;
import com.mycompany.xtremeo.client.ui.dialog.ModalDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;


public class HistoryDialogController {

    @FXML private VBox dialogRoot;
    @FXML private Button closeBtn;
    @FXML private ListView<GameHistoryEntry> historyListView;
    GameFileService fileService;

    private ModalDialog dialog;
    private boolean onlineGamesOnly = false;
    private String playerUsername = null;

    @FXML
    public void initialize() {
        historyListView.setCellFactory(list -> new GameHistoryCellController());
        historyListView.setFocusTraversable(false);
        fileService = new GameFileService(new JsonFileHandler());

        Platform.runLater(this::loadSampleData);
    }

    public void setDialog(ModalDialog dialog) {
        this.dialog = dialog;
    }

    public void setFilterMode(boolean onlineGamesOnly, String username) {
        this.onlineGamesOnly = onlineGamesOnly;
        this.playerUsername = username;
    }

    @FXML
    void handleClose() {
        if (dialog != null) {
            dialog.close(null);
        }
    }

    private void loadSampleData() {
        List<GameHistoryEntry> history;
        
        if (onlineGamesOnly && playerUsername != null) {
            history = fileService.loadOnlineGames(playerUsername);
        } else {
            history = fileService.loadGames();
        }
        
        historyListView.getItems().clear();
        historyListView.getItems().addAll(history);
    }
}
