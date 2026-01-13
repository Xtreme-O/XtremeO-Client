/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.model.viewmodel;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.enums.GameState;
import com.mycompany.xtremeo.client.game.GameEngine;
import com.mycompany.xtremeo.client.game.GameOpponent;
import com.mycompany.xtremeo.client.game.OnlineOpponent;
import com.mycompany.xtremeo.client.game.TicTacToeCpuOpponent;
import com.mycompany.xtremeo.client.model.game.*;
import com.mycompany.xtremeo.client.model.viewmodel.listeners.OnGameOverListener;
import com.mycompany.xtremeo.client.model.viewmodel.listeners.OnMoveMadeListener;
import com.mycompany.xtremeo.client.service.game.SessionMessageService;
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

    private InGamePlayer localPlayer;
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
        if (isRecording) {
            this.recorderService = new GameRecorderService();
        } else {
            this.recorderService = null;
        }
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

        if (localPlayer != null && localPlayer.symbol() != null && localPlayer.symbol().equals("X")) {
            currentPlayer = localPlayer;
        } else if (secondPlayer != null && secondPlayer.symbol() != null && secondPlayer.symbol().equals("X")) {
            currentPlayer = secondPlayer;
        } else {
            currentPlayer = localPlayer;
        }

        gameLog.clear();

        if (isReplayMode) {
            statusMessage.set("Replay: Click or Play to start");
        } else {
            if (currentPlayer != null) {
                statusMessage.set(currentPlayer.name() + "'s Turn");
                gameLog.add("New Game Started!");
                if (recorderService != null && localPlayer != null && secondPlayer != null) {
                    recorderService.startRecording(localPlayer, secondPlayer);
                }
            } else {
                statusMessage.set("Waiting to start...");
            }
        }
    }

    public void setGameMode(GameMode mode, Difficulty difficulty, GameSession session) {
        this.difficulty = difficulty;
        this.isGameOver = false;
        resetBoard();
        switch (mode) {
            case WITH_CPU:
                localPlayer = InGamePlayer.localOfflinePlayer();
                this.secondPlayer = InGamePlayer.cpuPlayer();
                this.opponent = new TicTacToeCpuOpponent(difficulty, localPlayer, secondPlayer);
                break;
            case ONLINE_PLAYER:
                this.localPlayer = session.getLocalPlayer();
                this.secondPlayer = session.getOpponentPlayer();
                this.opponent = session.getOpponent();
                if (this.opponent instanceof OnlineOpponent) {
                    ((OnlineOpponent) this.opponent).setOpponentPlayer(this.secondPlayer);
                }
                break;
            case WITH_FRIEND:
            default:
                this.localPlayer = InGamePlayer.localOfflinePlayer();
                this.secondPlayer = InGamePlayer.opponentOfflinePlayer();
                this.opponent = null;
                break;
        }

        if (localPlayer != null && localPlayer.symbol() != null && localPlayer.symbol().equals("X")) {
            currentPlayer = localPlayer;
        } else if (secondPlayer != null && secondPlayer.symbol() != null && secondPlayer.symbol().equals("X")) {
            currentPlayer = secondPlayer;
        } else {
            currentPlayer = localPlayer;
        }

        if (currentPlayer != localPlayer) {
            statusMessage.set(currentPlayer.name() + "'s Turn");
        }else{
            statusMessage.set("Your Turn");
        }

        if (recorderService != null) {
            recorderService.startRecording(localPlayer, secondPlayer);
        }

        // Request move if it's the opponent's turn
        requestMove();
    }

    public void makeMove(Move move) {
        System.out.println("makeMove called with: " + move);
        int row = move.row();
        int col = move.col();
        InGamePlayer playerWhoMoved = move.player();
        if (isGameOver || board[row][col] == null || !board[row][col].isEmpty()) {
            System.err.println("makeMove returning early - isGameOver: " + isGameOver + ", board[" + row + "][" + col + "] = " + board[row][col]);
            return;
        }

        if (!isReplayMode && recorderService != null) {
            recorderService.recordMove(move);
        }

        String symbol = playerWhoMoved.symbol();
        if (symbol == null) {
            System.err.println("ERROR: Player symbol is null in makeMove!");
            return;
        }

        board[row][col] = symbol;
        gameLog.addFirst(
                playerWhoMoved.name() + " placed " + symbol + " at [" + row + "," + col + "]");

        winningLine = engine.getWinningLine(board, row, col);
        GameState gameState;

        if (winningLine != null) {
            isGameOver = true;
            gameState = GameState.WIN;
            if(playerWhoMoved == localPlayer) {
                statusMessage.set("You Wins");
            }else{
                statusMessage.set(playerWhoMoved.name() + " Wins!");

            }
            updateScore(playerWhoMoved.symbol());
            if (!isReplayMode && gameOverListener != null) {
                gameOverListener.onGameOver(localPlayer, secondPlayer, playerWhoMoved);
            }
        } else if (engine.isBoardFull(board)) {
            isGameOver = true;
            gameState = GameState.DRAW;
            statusMessage.set("It's a Draw!");
            if (!isReplayMode && gameOverListener != null) {
                gameOverListener.onGameOver(localPlayer, secondPlayer, null);
            }
        } else {
            gameState = GameState.IN_PROGRESS;
            currentPlayer = (playerWhoMoved.equals(localPlayer)) ? secondPlayer : localPlayer;
            if(isReplayMode) {
                statusMessage.set("Move " + gameLog.size() + " - " + currentPlayer.name() + "'s Turn");
            }else{
                if(currentPlayer == localPlayer) {
                    statusMessage.set("Your Turn");


                }else{
                    statusMessage.set( currentPlayer.name() + "'s Turn");

                }
            }
        }

        if (opponent instanceof OnlineOpponent && playerWhoMoved.equals(localPlayer)) {
            SessionMessageService.getInstance().sendMove(getSessionMove(move), gameState);
        }

        if (moveListener != null) {
            System.out.println("Calling moveListener.onMoveMade");
            moveListener.onMoveMade(move);
        } else {
            System.err.println("WARNING: moveListener is null!");
        }

        requestMove();
        System.out.println("makeMove completed successfully");
    }

    public SessionMove getSessionMove(Move move) {
        return new SessionMove(
                new GamePlayerResponse(
                        move.player().name(),
                        move.player().symbol()),
                        move.row(), move.col());
    }

    public void requestMove() {
        System.out.println("requestMove called - isReplayMode: " + isReplayMode + ", isGameOver: " + isGameOver + ", opponent: " + opponent + ", isCurrentPlayer: " + isCurrentPlayer());
        if (!isReplayMode && !isGameOver && opponent != null) {
            if (!isCurrentPlayer()) {
                System.out.println("Registering callback for opponent move...");
                new Thread(() -> {
                    opponent.requestMove(board, (movement) -> {
                        javafx.application.Platform.runLater(() -> makeMove(movement));
                    });
                }).start();
            } else {
                System.out.println("Skipping requestMove - it's current player's turn");
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
