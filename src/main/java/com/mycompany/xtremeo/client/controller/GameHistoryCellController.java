package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.game.GameHistoryEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class GameHistoryCellController extends ListCell<GameHistoryEntry> {

    private static final String FXML_PATH = "/com/mycompany/xtremeo/client/view/history-item.fxml";

    @FXML private HBox root;
    @FXML private StackPane iconContainer;
    @FXML private FontIcon resultIcon;
    @FXML private Label resultLabel;
    @FXML private Label opponentLabel;
    @FXML private Label timeLabel;

    private GameHistoryEntry currentItem;

    public GameHistoryCellController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlePlay() {
        if (currentItem != null) {
            System.out.println("Replay: " + currentItem.player2());
        }
    }

    @Override
    protected void updateItem(GameHistoryEntry item, boolean empty) {
        super.updateItem(item, empty);
        currentItem = item;
        if (empty || item == null) {
            setGraphic(null);
            return;
        }

        iconContainer.getStyleClass().removeAll("history-icon-win", "history-icon-lose", "history-icon-draw");

        switch (item.result()) {
            case WIN -> {
                resultIcon.setIconCode(MaterialDesignT.TROPHY);
                resultLabel.setText("Victory");
                iconContainer.getStyleClass().add("history-icon-win");
            }
            case LOSE -> {
                resultIcon.setIconCode(MaterialDesignC.CLOSE_THICK);
                resultLabel.setText("Defeat");
                iconContainer.getStyleClass().add("history-icon-lose");
            }
            case DRAW -> {
                resultIcon.setIconCode(MaterialDesignH.HANDSHAKE);
                resultLabel.setText("Draw");
                iconContainer.getStyleClass().add("history-icon-draw");
            }
            default -> {
                resultIcon.setIconCode(MaterialDesignH.HELP);
                resultLabel.setText("Unknown");
            }
        }

        opponentLabel.setText(item.player2().name());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timeLabel.setText(item.time().format(formatter));
        setGraphic(root);
    }
}

