package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.model.game.*;
import com.mycompany.xtremeo.client.model.viewmodel.GameReplayDriver;
import com.mycompany.xtremeo.client.model.viewmodel.GameViewModel;
import com.mycompany.xtremeo.client.service.audio.AudioService;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
import com.mycompany.xtremeo.client.service.video.VideoService;
import com.mycompany.xtremeo.client.util.AudioFiles;
import com.mycompany.xtremeo.client.util.Screen;
import com.mycompany.xtremeo.client.util.UIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

public class BoardController {
    @FXML
    private Label scoreX, scoreO;
    @FXML
    private Label turnLabel;
    @FXML
    private HBox turnIndicatorContainer;
    @FXML
    private VBox logContainer;
    @FXML
    private GridPane gameGrid;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnHistory;
    @FXML
    private Button btnBack;

    @FXML
    private MediaView mediaView;

    private GameViewModel viewModel;
    private GameReplayDriver replayDriver;
    private final Button[][] buttons = new Button[3][3];

    private GameMode selectedMode = GameMode.WITH_FRIEND;

    @FXML
    public void initialize() {
    }

    public void init(GameMode mode, Difficulty difficulty, boolean record, GameSession session) {
        this.selectedMode = mode;
        viewModel = new GameViewModel(record);
        viewModel.setGameMode(selectedMode, difficulty, session);
        setup();
    }


    public void init(GameMode mode, boolean record, GameSession session) {
        init(mode, Difficulty.NONE, record, session);
        if(selectedMode == GameMode.ONLINE_PLAYER) {
            btnReset.setVisible(false);
        }
    }

    public void initReplay(GameHistoryEntry history) {
        viewModel = new GameViewModel(history);
        replayDriver = new GameReplayDriver(viewModel, history);
        setup();
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

    private void setupGrid() {
        gameGrid.getChildren().forEach(node -> {
            if (node instanceof Button btn) {
                int r = GridPane.getRowIndex(btn) == null ? 0 : GridPane.getRowIndex(btn);
                int c = GridPane.getColumnIndex(btn) == null ? 0 : GridPane.getColumnIndex(btn);
                buttons[r][c] = btn;
                if (viewModel != null && !viewModel.isReplayMode()) {
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

        String symbol = move.player().symbol();
        if (symbol == null) {
            System.err.println("ERROR: Player symbol is null for move!");
            return;
        }

        btn.getStyleClass().remove("preview-symbol");
        btn.setText(symbol);
        btn.getStyleClass().add(symbol.equals("X") ? "filled-x" : "filled-o");
        btn.setDisable(true);
        String positionName = com.mycompany.xtremeo.client.util.GamePosition.getPositionName(move.row(), move.col());
        String logMessage = String.format("Player %s marked %s", symbol, positionName);
        addLogCard(logMessage);
        if (viewModel.isGameOver()) {
            disableEntireBoard();
            if (viewModel.isGameWon()) {
                turnLabel.getStyleClass().add("status-win");
                highlightWinningLine();
            }
        }
    }

    private void onGameOver(InGamePlayer p1, InGamePlayer p2, InGamePlayer winner) {
        AudioService audioService = AudioService.getInstance();
        VideoService videoService = VideoService.getInstance();
        InGamePlayer localPlayer = viewModel.getLocalPlayer();
        if (winner == null) {
            videoService.playVideo(mediaView, "draw_video.mp4");
            System.out.println("The game ended in a draw between " + p1.name() + " and " + p2.name());
        } else {
            if (selectedMode != GameMode.WITH_FRIEND) {
                if (winner.symbol().equals(localPlayer.symbol())) {
                    System.out.println("you wins");
                    videoService.playVideo(mediaView, "win_video.mp4");
                    audioService.playSoundEffect(AudioFiles.WIN_SOUND);
                } else {
                    System.out.println("u loses");
                    videoService.playVideo(mediaView, "lose_video.mp4");
                }
            }
            else{audioService.playSoundEffect(AudioFiles.WIN_SOUND);}
            System.out.println("Winner is: " + winner.name());
        }
        disableEntireBoard();
        viewModel.saveRecording(winner);
    }

    @FXML
    void handleCellClick(ActionEvent event) {
        if (viewModel == null) {
            return;
        }
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
        if (viewModel != null && viewModel.isReplayMode() && replayDriver != null) {
            replayDriver.stopAutoPlay();
        }
        if(selectedMode == GameMode.ONLINE_PLAYER ||
                PlayerService.getInstance().getCurrentPlayer() != null){
            Navigator.setRoot(Screen.LOBBY.getName());
            return;
        }
        Navigator.setRoot(Screen.MAIN.getName());
    }

    @FXML
    void handleReset(ActionEvent event) {
        btnReset.setVisible(true);
        if (viewModel == null) {
            return;
        }
        if (viewModel.isReplayMode()) {
            if (replayDriver != null && replayDriver.isPlayingProperty().get()) {
                replayDriver.stopAutoPlay();
            } else {
                if (replayDriver != null) {
                    if (!replayDriver.hasNext()) {
                        clearBoard();
                        replayDriver.restart();
                    }
                    replayDriver.startAutoPlay();
                }
            }
        } else {
            viewModel.resetBoard();
            clearBoard();
        }
    }

    private void clearBoard() {
        logContainer.getChildren().clear();
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

    private void addLogCard(String message) {
        logContainer.getChildren().add(UIUtils.createLogCard(message));
    }

    private void handleHoverEnter(Button btn) {
        String buttonText = btn.getText();
        if (viewModel != null && !btn.isDisable() && (buttonText == null || buttonText.isEmpty())) {
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
