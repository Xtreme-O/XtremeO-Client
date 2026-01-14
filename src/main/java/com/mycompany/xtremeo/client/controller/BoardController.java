package com.mycompany.xtremeo.client.controller;
import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.model.game.GameHistoryEntry;
import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.model.game.GameSession;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.model.viewmodel.GameReplayDriver;
import com.mycompany.xtremeo.client.model.viewmodel.GameViewModel;
import com.mycompany.xtremeo.client.protocol.handler.game.SessionEndHandler;
import com.mycompany.xtremeo.client.service.audio.AudioService;
import com.mycompany.xtremeo.client.service.game.SessionMessageService;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
import com.mycompany.xtremeo.client.service.video.VideoService;
import com.mycompany.xtremeo.client.ui.dialog.ErrorDialog;
import com.mycompany.xtremeo.client.util.AudioFiles;
import com.mycompany.xtremeo.client.util.Screen;
import com.mycompany.xtremeo.client.util.UIUtils;
import javafx.application.Platform;
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
    private GameHistoryEntry replayHistoryEntry;

    @FXML
    public void initialize() {
        SessionEndHandler.onSessionEnded(() -> {
            Platform.runLater(()-> {
                Navigator.setRoot(Screen.LOBBY.getName());
                ErrorDialog.showServerError("One of the players has ended the session");

            });
        });
    }

    public void init(GameMode mode, Difficulty difficulty, boolean record, GameSession session) {
        this.selectedMode = mode;
        boolean shouldRecord = record || (mode == GameMode.ONLINE_PLAYER);
        viewModel = new GameViewModel(shouldRecord);
        viewModel.setGameMode(selectedMode, difficulty, session);
        if (mode == GameMode.ONLINE_PLAYER) {
            String username = PlayerService.getInstance().getUsername();
            viewModel.setPlayerUsername(username);
        }
        setup();
        initChatPanel();
    }


    public void init(GameMode mode, boolean record, GameSession session) {
        init(mode, Difficulty.NONE, record, session);
        if(selectedMode == GameMode.ONLINE_PLAYER) {
            btnReset.setVisible(false);
        }
    }

    public void initReplay(GameHistoryEntry history) {
        this.selectedMode = GameMode.WITH_FRIEND;
        this.replayHistoryEntry = history;
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

            if (selectedMode == GameMode.ONLINE_PLAYER) {
                InGamePlayer localPlayer = viewModel.getLocalPlayer();
                InGamePlayer secondPlayer = viewModel.getSecondPlayer();

                InGamePlayer xPlayer = null;
                InGamePlayer oPlayer = null;

                if (localPlayer != null && "X".equals(localPlayer.symbol())) {
                    xPlayer = localPlayer;
                    oPlayer = secondPlayer;
                } else if (localPlayer != null && "O".equals(localPlayer.symbol())) {
                    xPlayer = secondPlayer;
                    oPlayer = localPlayer;
                } else if (secondPlayer != null && "X".equals(secondPlayer.symbol())) {
                    xPlayer = secondPlayer;
                    oPlayer = localPlayer;
                } else if (secondPlayer != null && "O".equals(secondPlayer.symbol())) {
                    xPlayer = localPlayer;
                    oPlayer = secondPlayer;
                }

                if (xPlayer != null) {
                    scoreX.setText(xPlayer.name());
                }
                if (oPlayer != null) {
                    scoreO.setText(oPlayer.name());
                }
            } else {
                scoreX.textProperty().bind(viewModel.playerXScoreProperty().asString());
                scoreO.textProperty().bind(viewModel.playerOScoreProperty().asString());
            }

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
            if (selectedMode != GameMode.WITH_FRIEND) {
                InGamePlayer localPlayer = viewModel.getLocalPlayer();
                if (localPlayer != null && winner.symbol().equals(localPlayer.symbol())) {
                    System.out.println("you wins");
                    playWinVideo();
                    audioService.playSoundEffect(AudioFiles.WIN_SOUND);
                } else {
                    System.out.println("u loses");
                    playLoseVideo();
                    audioService.playSoundEffect(AudioFiles.LOSE_SOUND);
                }
            } else {
                audioService.playSoundEffect(AudioFiles.WIN_SOUND);
            }
            System.out.println("The winner is: " + winner.name());
        }
        disableEntireBoard();
        viewModel.saveRecording(winner);
    }

    private void playWinVideo() {
        javafx.scene.media.MediaView mediaView = new javafx.scene.media.MediaView();
        addMediaViewToScene(mediaView);
        VideoService.getInstance().playVideo("win_video.mp4", "You Won!");    }

    private void playLoseVideo() {
        javafx.scene.media.MediaView mediaView = new javafx.scene.media.MediaView();
        addMediaViewToScene(mediaView);
        VideoService.getInstance().playVideo("draw_video.mp4", "It's a Draw!");
    }

    private void addMediaViewToScene(javafx.scene.media.MediaView mediaView) {
        if (gameGrid == null || gameGrid.getScene() == null) {
            return;
        }

        javafx.scene.Parent root = gameGrid.getScene().getRoot();

        // If root is already a StackPane, add MediaView to it
        if (root instanceof javafx.scene.layout.StackPane stackPane) {
            stackPane.getChildren().add(mediaView);
            return;
        }

        // Otherwise, wrap root in a StackPane and add MediaView
        javafx.scene.layout.StackPane wrapper = new javafx.scene.layout.StackPane();
        wrapper.getChildren().add(root);
        wrapper.getChildren().add(mediaView);
        gameGrid.getScene().setRoot(wrapper);
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

        if (viewModel != null && viewModel.isReplayMode()) {
            if (replayHistoryEntry != null && replayHistoryEntry.gameMode() == GameMode.ONLINE_PLAYER) {
                Navigator.setRoot(Screen.LOBBY.getName());
            } else {
                Navigator.setRoot(Screen.MAIN.getName());
            }
            return;
        }

        if (selectedMode == GameMode.ONLINE_PLAYER && PlayerService.getInstance().getCurrentPlayer() != null) {
            SessionMessageService.getInstance().sendEndSessionMessage();
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