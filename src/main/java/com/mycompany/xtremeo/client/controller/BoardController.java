package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.model.GameMode;
import com.mycompany.xtremeo.client.model.viewmodel.GameViewModel;
import com.mycompany.xtremeo.client.util.UIUtils;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoardController {
    @FXML private Label scoreX, scoreO;
    @FXML private Label turnLabel;
    @FXML private HBox turnIndicatorContainer;
    @FXML private VBox logContainer;
    @FXML private GridPane gameGrid;
    @FXML private Button btnReset;
    @FXML private Button btnHistory;
    @FXML private Button btnBack;

    private GameViewModel viewModel;
    public static GameMode selectedMode=GameMode.WITH_FRIEND;
    private Button[][] buttons = new Button[3][3];

    @FXML
    public void initialize() {
        viewModel = new GameViewModel();
        if (selectedMode == GameMode.WITH_CPU) {
            viewModel.setGameMode(selectedMode, MainMenuController.selectedDifficulty);
        } else {
            viewModel.setGameMode(selectedMode);
        }

        viewModel.setOnMoveMadeListener((r, c, symbol) -> {
            Button btn = getButtonAt(r, c);
            if (btn != null) {
                btn.setText(symbol);
                btn.getStyleClass().add(symbol.equals("X") ? "filled-x" : "filled-o");
                btn.setDisable(true);

                if (viewModel.isGameOver()) {
                    disableEntireBoard();
                    if (viewModel.isGameWon()) {
                        turnLabel.getStyleClass().add("status-win");
                        highlightWinningLine();
                    }
                }
            }
        });

        scoreX.textProperty().bind(viewModel.playerXScoreProperty().asString());
        scoreO.textProperty().bind(viewModel.playerOScoreProperty().asString());
        turnLabel.textProperty().bind(viewModel.statusMessageProperty());
        UIUtils.setupPulseAnimation(turnIndicatorContainer);

        viewModel.getGameLog().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(this::addLogCard);
                }
            }
        });

        gameGrid.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button btn =(Button) node;
                Integer r = GridPane.getRowIndex(btn);
                Integer c = GridPane.getColumnIndex(btn);
                buttons[r == null ? 0 : r][c == null ? 0 : c] = btn;

                btn.setOnMouseEntered(e -> handleHoverEnter(btn));
                btn.setOnMouseExited(e -> handleHoverExit(btn));
            }
        });
    }



    @FXML
    void handleCellClick(ActionEvent event) {
        Button clickedBtn = (Button) event.getSource();
        if (selectedMode != GameMode.WITH_FRIEND && viewModel.getCurrentPlayerSymbol().equals("O")) {
            //System.out.println("It's not your turn! wait for the opponent");
            return;
        }
        clickedBtn.getStyleClass().remove("preview-symbol");
        Integer row = GridPane.getRowIndex(clickedBtn);
        Integer col = GridPane.getColumnIndex(clickedBtn);

        viewModel.makeMove(row == null ? 0 : row, col == null ? 0 : col);

    }

    @FXML
    void handleBack(ActionEvent event) {
        Navigator.setRoot("main-menu.fxml");
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

    private void highlightWinningLine() {
        int[][] line = viewModel.getWinningLine();
        if (line != null) {
            for (int[] pos : line) {
                Button btn = getButtonAt(pos[0], pos[1]);
                if (btn != null) btn.getStyleClass().add("winning-cell");
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
        logContainer.getChildren().add(0, UIUtils.createLogCard(message));
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
    private Button getButtonAt(int row, int col) {
        return buttons[row][col];
    }


}