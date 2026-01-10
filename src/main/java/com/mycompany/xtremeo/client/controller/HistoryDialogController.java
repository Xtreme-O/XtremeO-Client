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


public class HistoryDialogController {

    @FXML private VBox dialogRoot;
    @FXML private Button closeBtn;
    @FXML private ListView<GameHistoryEntry> historyListView;
    GameFileService fileService;


    private ModalDialog dialog;

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

    @FXML
    void handleClose() {
        if (dialog != null) {
            dialog.close(null);
        }
    }

    private void loadSampleData() {
        var history = fileService.loadGames();
        historyListView.getItems().clear();
        historyListView.getItems().addAll(history);
    }
}
