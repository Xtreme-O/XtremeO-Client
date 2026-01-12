/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.model.viewmodel;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.game.GameEngine;
import com.mycompany.xtremeo.client.game.GameOpponent;
import com.mycompany.xtremeo.client.game.OnlineOpponent;
import com.mycompany.xtremeo.client.game.TicTacToeCpuOpponent;
import com.mycompany.xtremeo.client.model.game.*;
import com.mycompany.xtremeo.client.model.viewmodel.listeners.OnGameOverListener;
import com.mycompany.xtremeo.client.model.viewmodel.listeners.OnMoveMadeListener;
import com.mycompany.xtremeo.client.service.recording.GameRecorderService;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class GameViewModel {

    private OnMoveMadeListener moveListener;
    private OnGameOverListener gameOverListener;

    private final InGamePlayer localPlayer;
    private InGamePlayer secondPlayer;
    private InGamePlayer currentPlayer;

    private GameOpponent opponent;
    private final GameEngine engine = new GameEngine();
    private final String[][] board = new String[3][3];
    private boolean isGameOver = false;

    private final StringProperty statusMessage = new SimpleStringProperty();
    private final IntegerProperty playerXScore = new SimpleIntegerProperty(0);
    private final IntegerProperty playerOScore = new SimpleIntegerProperty(0);

    private final ObservableList<String> gameLog = FXCollections.observableArrayList();
    private int[][] winningLine = null;

    private final GameRecorderService recorderService;
    private boolean isReplayMode = false;
    private Difficulty difficulty;

    public GameViewModel(boolean isRecording) {
        this.localPlayer = new InGamePlayer("Player 1", "X");
        this.secondPlayer = new InGamePlayer("Player 2", "O");
        if (isRecording) {
            this.recorderService = new GameRecorderService();
        } else {
            this.recorderService = null;
        }
        currentPlayer = localPlayer;
        resetBoard();
    }

    public GameViewModel(GameHistoryEntry history) {
        this.isReplayMode = true;
        this.recorderService = null;
        this.localPlayer = history.player1();
        this.secondPlayer = history.player2();
        currentPlayer = localPlayer;
        resetBoard();
        statusMessage.set("Replay: Click Play to start");
    }

    public void setOnMoveMadeListener(OnMoveMadeListener listener) {
        this.moveListener = listener;
    }

    public void setOnGameOverListener(OnGameOverListener listener) {
        this.gameOverListener = listener;
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
        isGameOver = false;
        winningLine = null;
        currentPlayer = localPlayer;
        gameLog.clear();

        if (isReplayMode) {
            statusMessage.set("Replay: Click or Play to start");
        } else {
            statusMessage.set(currentPlayer.name() + "'s Turn ");
            gameLog.add("New Game Started!");
            if (recorderService != null) {
                recorderService.startRecording(localPlayer, secondPlayer);
            }
        }
    }

    public void setGameMode(GameMode mode, Difficulty difficulty) {
        this.difficulty = difficulty;
        this.isGameOver = false;
        resetBoard();
        switch (mode) {
            case WITH_CPU:
                this.secondPlayer = new InGamePlayer("CPU", "O");
                this.opponent = new TicTacToeCpuOpponent(difficulty, localPlayer, secondPlayer);
                break;
            case ONLINE_PLAYER:
                this.secondPlayer = new InGamePlayer("Player 2", "O");
                 this.opponent = OnlineOpponent.getInstance();
                break;
            case WITH_FRIEND:
            default:
                this.secondPlayer = new InGamePlayer("Player 2", "O");
                this.opponent = null;
                break;
        }
        if (recorderService != null) {
            recorderService.startRecording(localPlayer, secondPlayer);
        }
    }

    public void makeMove(Move move) {
        int row = move.row();
        int col = move.col();
        InGamePlayer playerWhoMoved = move.player();
        if (isGameOver || !board[row][col].isEmpty())
            return;

        if (!isReplayMode && recorderService != null) {
            recorderService.recordMove(move);
        }

        board[row][col] = playerWhoMoved.symbol();
        gameLog.addFirst(
                playerWhoMoved.name() + " placed " + playerWhoMoved.symbol() + " at [" + row + "," + col + "]");

        winningLine = engine.getWinningLine(board, row, col);

        if (winningLine != null) {
            isGameOver = true;
            statusMessage.set(playerWhoMoved.name() + " Wins!");
            updateScore(playerWhoMoved.symbol());
            if (!isReplayMode && gameOverListener != null) {
                gameOverListener.onGameOver(localPlayer, secondPlayer, playerWhoMoved);
            }
        } else if (engine.isBoardFull(board)) {
            isGameOver = true;
            statusMessage.set("It's a Draw!");
            if (!isReplayMode && gameOverListener != null) {
                gameOverListener.onGameOver(localPlayer, secondPlayer, null);
            }
        } else {
            currentPlayer = (playerWhoMoved.equals(localPlayer)) ? secondPlayer : localPlayer;
            statusMessage.set(isReplayMode
                ? "Move " + gameLog.size() + " - " + currentPlayer.name() + "'s Turn"
                : currentPlayer.name() + "'s Turn");
        }

        if (moveListener != null) {
            moveListener.onMoveMade(move);
        }

        requestMove();
    }

    public void requestMove() {
        if (!isReplayMode && !isGameOver && opponent != null) {
            if(!isCurrentPlayer()) {
                new Thread(() -> {
                    opponent.requestMove(board, (movement) -> {
                        javafx.application.Platform.runLater(() -> makeMove(movement));
                    });
                }).start();
            }
        }
    }

    private void updateScore(String winner) {
        if (winner.equals("X"))
            playerXScore.set(playerXScore.get() + 1);
        else
            playerOScore.set(playerOScore.get() + 1);
    }

    public boolean isCurrentPlayer() {
        return Objects.equals(currentPlayer.symbol(), localPlayer.symbol());
    }

    public StringProperty statusMessageProperty() {
        return statusMessage;
    }

    public IntegerProperty playerXScoreProperty() {
        return playerXScore;
    }

    public IntegerProperty playerOScoreProperty() {
        return playerOScore;
    }

    public ObservableList<String> getGameLog() {
        return gameLog;
    }

    public String getCurrentPlayerSymbol() {
        return currentPlayer.symbol();
    }

    public boolean isGameWon() {
        return isGameOver && !statusMessage.get().contains("Draw");
    }

    public int[][] getWinningLine() {
        return winningLine;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public InGamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void saveRecording(InGamePlayer winner) {
        if (recorderService != null) {
            recorderService.saveGame(getGameResult(winner), winner, difficulty);
        }
    }

    public GameResult getGameResult(InGamePlayer winner) {
        if (!isGameOver) {
            return GameResult.ONGOING;
        }

        if (winningLine != null) {
            return winner == localPlayer ? GameResult.WIN : GameResult.LOSE;
        } else {
            return GameResult.DRAW;
        }
    }

    public boolean isReplayMode() {
        return isReplayMode;
    }

    public InGamePlayer getLocalPlayer() {
        return localPlayer;
    }

    public InGamePlayer getSecondPlayer() {
        return secondPlayer;
    }

}
