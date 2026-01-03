package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.strategy.GameMode;
import com.mycompany.xtremeo.client.model.viewmodel.GameViewModel;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class BoardController {
    @FXML private Label scoreX, scoreO;
    @FXML private Label turnLabel;
    @FXML private HBox turnIndicatorContainer;
    @FXML private VBox logContainer;
    @FXML private GridPane gameGrid;
    @FXML private Button btnReset;
    @FXML private Button btnHistory;

    private GameViewModel viewModel;
    public static GameMode selectedMode=GameMode.WITH_FRIEND;

    @FXML
    public void initialize() {
        viewModel = new GameViewModel();
        viewModel.setGameMode(selectedMode);

        scoreX.textProperty().bind(viewModel.playerXScoreProperty().asString());
        scoreO.textProperty().bind(viewModel.playerOScoreProperty().asString());
        turnLabel.textProperty().bind(viewModel.statusMessageProperty());
        setupPulseAnimation();

        viewModel.getGameLog().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(this::addLogCard);
                }
            }
        });

        gameGrid.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button btn = (Button) node;

                btn.setOnMouseEntered(e -> handleHoverEnter(btn));
                btn.setOnMouseExited(e -> handleHoverExit(btn));
            }
        });
    }

    private void handleHoverEnter(Button btn) {
        if (!btn.isDisable() && btn.getText().isEmpty()) {
            String currentSymbol = viewModel.getCurrentPlayerSymbol();
            btn.setText(currentSymbol);
            btn.getStyleClass().add("preview-symbol");
        }
    }

    private void handleHoverExit(Button btn) {
        if (btn.getStyleClass().contains("preview-symbol")) {
            btn.setText("");
            btn.getStyleClass().remove("preview-symbol");
        }
    }


    private void setupPulseAnimation() {
        FadeTransition pulse = new FadeTransition(Duration.millis(1000), turnIndicatorContainer);
        pulse.setFromValue(1.0);
        pulse.setToValue(0.5);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
    }

    @FXML
    void handleCellClick(ActionEvent event) {
        Button clickedBtn = (Button) event.getSource();
        clickedBtn.getStyleClass().remove("preview-symbol");
        Integer row = GridPane.getRowIndex(clickedBtn);
        Integer col = GridPane.getColumnIndex(clickedBtn);

        int r = (row == null) ? 0 : row;
        int c = (col == null) ? 0 : col;

        String symbol = viewModel.makeMove(r, c);
        if (symbol != null) {
            clickedBtn.setText(symbol);
            clickedBtn.getStyleClass().add(symbol.equals("X") ? "filled-x" : "filled-o");
            clickedBtn.setDisable(true);
        if(viewModel.isGameOver()) {
            disableEntireBoard();
                if (viewModel.isGameWon()) {
                    turnLabel.getStyleClass().add("status-win");

                    int[][] line = viewModel.getWinningLine();
                    for (int[] pos : line) {
                        Button btn = getButtonAt(pos[0], pos[1]);
                        if (btn != null) btn.getStyleClass().add("winning-cell");
                    }
                }
            }
        }
    }
    private void disableEntireBoard() {
        gameGrid.getChildren().forEach(node -> {
            if (node instanceof Button) {
                node.setDisable(true);
            }
        });
    }

    private void addLogCard(String message) {
        HBox card = new HBox();
        card.getStyleClass().add("log-item-card");
        card.setAlignment(Pos.CENTER_LEFT);

        Label msgLabel = new Label(message);
        msgLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

        card.getChildren().add(msgLabel);
        logContainer.getChildren().add(0, card);
    }

    @FXML
    void handleReset(ActionEvent event) {
        viewModel.resetBoard();
        logContainer.getChildren().clear();
        turnLabel.getStyleClass().remove("status-win");
        gameGrid.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button btn = (Button) node;
                btn.setText("");
                btn.setDisable(false);
                btn.getStyleClass().removeAll("filled-x", "filled-o", "winning-cell", "preview-symbol");
            }
        });
    }

    private Button getButtonAt(int row, int col) {
        for (javafx.scene.Node node : gameGrid.getChildren()) {
            if (node instanceof Button) {
                Integer r = GridPane.getRowIndex(node);
                Integer c = GridPane.getColumnIndex(node);

                if (r != null && r == row && c != null && c == col) {
                    return (Button) node;
                }
            }
        }
        return null;
    }
}