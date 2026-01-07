package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.GameHistoryEntry;
import com.mycompany.xtremeo.client.model.GameResult;
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

    private ModalDialog dialog;

    @FXML
    public void initialize() {
        historyListView.setCellFactory(list -> new GameHistoryCellController());
        historyListView.setFocusTraversable(false);
        
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
        historyListView.getItems().addAll(
            new GameHistoryEntry(GameResult.WIN, "vs CPU (Hard)", "2m ago"),
            new GameHistoryEntry(GameResult.LOSE, "vs Player Two", "1h ago"),
            new GameHistoryEntry(GameResult.DRAW, "vs CPU (Medium)", "Yesterday"),
            new GameHistoryEntry(GameResult.WIN, "vs CPU (Medium)", "2d ago"),
            new GameHistoryEntry(GameResult.LOSE, "vs CPU (Expert)", "1w ago")
        );
    }
}
