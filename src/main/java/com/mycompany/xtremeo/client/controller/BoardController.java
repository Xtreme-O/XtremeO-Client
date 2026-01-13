package com.mycompany.xtremeo.client.controller;
import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.controller.BoardChatController;
import com.mycompany.xtremeo.client.model.game.GameHistoryEntry;
import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.model.viewmodel.GameReplayDriver;
import com.mycompany.xtremeo.client.model.viewmodel.GameViewModel;
import com.mycompany.xtremeo.client.service.audio.AudioService;
import com.mycompany.xtremeo.client.util.AudioFiles;
import com.mycompany.xtremeo.client.util.Screen;
import com.mycompany.xtremeo.client.util.UIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

/**
 *
 * @author wahid
 */
public class BoardController {
    @FXML
    private Label scoreX, scoreO;
    @FXML
    private Label turnLabel;
    @FXML
    private HBox turnIndicatorContainer;
    @FXML
    private GridPane gameGrid;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnBack;
    @FXML
    private BoardChatController chatPanelController;

    private GameViewModel viewModel;
    private GameReplayDriver replayDriver;
    private final Button[][] buttons = new Button[3][3];

    private GameMode selectedMode = GameMode.WITH_FRIEND;

    @FXML
    public void initialize() {
    }

    public void init(GameMode mode, Difficulty difficulty, boolean record) {
        this.selectedMode = mode;
        viewModel = new GameViewModel(record);
        viewModel.setGameMode(selectedMode, difficulty);
        setup();
        initChatPanel();
    }

    public void init(GameMode mode, boolean record) {
        init(mode, Difficulty.NONE, record);
    }

    public void initReplay(GameHistoryEntry history) {
        this.selectedMode = GameMode.WITH_FRIEND;
        viewModel = new GameViewModel(history);
        replayDriver = new GameReplayDriver(viewModel, history);
        setup();
        initChatPanel();
        setPlayIcon(false);
    }

    private void setPlayIcon(boolean isPlaying) {
        FontIcon icon = new FontIcon(isPlaying ? MaterialDesignP.PAUSE : MaterialDesignP.PLAY);
        icon.setIconSize(24);
        btnReset.setGraphic(icon);
        btnReset.setText(null);
    }

    private void setup() {
        setupGrid();
        viewModel.setOnMoveMadeListener(this::onMoveMade);
        turnLabel.textProperty().bind(viewModel.statusMessageProperty());

        if (viewModel.isReplayMode()) {
            disableEntireBoard();
            replayDriver.isPlayingProperty().addListener((obs, wasPlaying, isPlaying) -> {
                setPlayIcon(isPlaying);
            });
            scoreX.setText(viewModel.getLocalPlayer().name());
            scoreO.setText(viewModel.getSecondPlayer().name());
        } else {
            viewModel.setOnGameOverListener(this::onGameOver);
            scoreX.textProperty().bind(viewModel.playerXScoreProperty().asString());
            scoreO.textProperty().bind(viewModel.playerOScoreProperty().asString());
            UIUtils.setupPulseAnimation(turnIndicatorContainer);
        }
    }

    private void initChatPanel() {
        if (chatPanelController != null) {
            chatPanelController.init(selectedMode, viewModel.getLocalPlayer(), viewModel.getSecondPlayer());
            
            if (selectedMode != GameMode.ONLINE_PLAYER) {
                chatPanelController.addSystemMessage("Game Started!");
            }
        }
    }

    private void setupGrid() {
        gameGrid.getChildren().forEach(node -> {
            if (node instanceof Button btn) {
                int r = GridPane.getRowIndex(btn) == null ? 0 : GridPane.getRowIndex(btn);
                int c = GridPane.getColumnIndex(btn) == null ? 0 : GridPane.getColumnIndex(btn);
                buttons[r][c] = btn;
                if (!viewModel.isReplayMode()) {
                    btn.setOnMouseEntered(e -> handleHoverEnter(btn));
                    btn.setOnMouseExited(e -> handleHoverExit(btn));
                }
            }
        });
    }

    private void onMoveMade(Move move) {
        Button btn = getButtonAt(move.row(), move.col());
        if (btn == null)
            return;

        btn.getStyleClass().remove("preview-symbol");
        btn.setText(move.player().symbol());
        btn.getStyleClass().add(move.player().symbol().equals("X") ? "filled-x" : "filled-o");
        btn.setDisable(true);

        addLogEntry(move);

        if (viewModel.isGameOver()) {
            disableEntireBoard();
            if (viewModel.isGameWon()) {
                turnLabel.getStyleClass().add("status-win");
                highlightWinningLine();
            }
        }
    }

    void addLogEntry(Move move) {
        if (selectedMode != GameMode.ONLINE_PLAYER && chatPanelController != null) {
            String logMessage = move.player().name() + " placed " + move.player().symbol() 
                    + " at [" + move.row() + "," + move.col() + "]";
            chatPanelController.addLogEntry(move.player(), logMessage);
        }
    }

    private void onGameOver(InGamePlayer p1, InGamePlayer p2, InGamePlayer winner) {
        AudioService audioService = AudioService.getInstance();
        if (winner == null) {
            System.out.println("The game ended in a draw between " + p1.name() + " and " + p2.name());
        } else {
            audioService.playSoundEffect(AudioFiles.WIN_SOUND);
            System.out.println("The winner is: " + winner.name());
        }
        disableEntireBoard();
        viewModel.saveRecording(winner);
    }

    @FXML
    void handleCellClick(ActionEvent event) {
        if (viewModel.isReplayMode()) {
            return;
        }

        if (selectedMode != GameMode.WITH_FRIEND && !viewModel.isCurrentPlayer()) {
            return;
        }
        Button clickedBtn = (Button) event.getSource();
        clickedBtn.getStyleClass().remove("preview-symbol");
        Integer row = GridPane.getRowIndex(clickedBtn);
        Integer col = GridPane.getColumnIndex(clickedBtn);

        viewModel.makeMove(new Move(viewModel.getCurrentPlayer(), row, col));
    }

    @FXML
    void handleBack(ActionEvent event) {
        if (viewModel.isReplayMode()) {
            replayDriver.stopAutoPlay();
        }
        Navigator.setRoot(Screen.MAIN.getName());
    }

    @FXML
    void handleReset(ActionEvent event) {
        if (viewModel.isReplayMode()) {
            if (replayDriver.isPlayingProperty().get()) {
                replayDriver.stopAutoPlay();
            } else {
                if (!replayDriver.hasNext()) {
                    clearBoard();
                    replayDriver.restart();
                }
                replayDriver.startAutoPlay();
            }
        } else {
            viewModel.resetBoard();
            clearBoard();
        }
    }

    private void clearBoard() {
        if (chatPanelController != null) {
            chatPanelController.clear();
            if (selectedMode != GameMode.ONLINE_PLAYER) {
                chatPanelController.addSystemMessage("New Game Started!");
            }
        }
        turnLabel.getStyleClass().remove("status-win");
        for (Button[] row : buttons) {
            for (Button btn : row) {
                btn.setText("");
                btn.setDisable(false);
                btn.getStyleClass().removeAll("filled-x", "filled-o", "winning-cell", "preview-symbol");
            }
        }
    }

    private void highlightWinningLine() {
        int[][] line = viewModel.getWinningLine();
        if (line != null) {
            for (int[] pos : line) {
                Button btn = getButtonAt(pos[0], pos[1]);
                if (btn != null)
                    btn.getStyleClass().add("winning-cell");
            }
        }
    }

    private void disableEntireBoard() {
        for (Button[] row : buttons) {
            for (Button btn : row) {
                btn.setDisable(true);
            }
        }
    }

    private void handleHoverEnter(Button btn) {
        if (!btn.isDisable() && btn.getText().isEmpty()) {
            btn.setText(viewModel.getCurrentPlayerSymbol());
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