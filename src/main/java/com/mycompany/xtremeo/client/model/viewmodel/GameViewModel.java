/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.model.viewmodel;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.game.GameEngine;
import com.mycompany.xtremeo.client.game.GameOpponent;
import com.mycompany.xtremeo.client.game.TicTacToeCpuOpponent;
import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.model.viewmodel.listeners.OnGameOverListener;
import com.mycompany.xtremeo.client.model.viewmodel.listeners.OnMoveMadeListener;
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

    private InGamePlayer player1;
    private  InGamePlayer player2;
    private InGamePlayer currentPlayer;

    private GameOpponent opponent;
    private final GameEngine engine = new GameEngine();
    private String[][] board = new String[3][3];
    private boolean isGameOver = false;

    private final StringProperty statusMessage = new SimpleStringProperty();
    private final IntegerProperty playerXScore = new SimpleIntegerProperty(0);
    private final IntegerProperty playerOScore = new SimpleIntegerProperty(0);

    private final ObservableList<String> gameLog = FXCollections.observableArrayList();
    private int[][] winningLine = null;
    public GameViewModel() {
        this.player1 = new InGamePlayer("Player 1", "X", false);
        this.player2 = new InGamePlayer("Player 2", "O", false);
        currentPlayer=player1;
        resetBoard();
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
        currentPlayer = player1;
        statusMessage.set(currentPlayer.name()+ "'s Turn ");
        gameLog.add("New Game Started!");
    }

    public void setGameMode(GameMode mode) {
        setGameMode(mode, Difficulty.HARD);
    }

    public void setGameMode(GameMode mode, Difficulty difficulty) {
        this.isGameOver = false;
        resetBoard();
        switch (mode) {
            case WITH_CPU:
                this.player2 = new InGamePlayer("CPU","O",true);
                this.opponent = new TicTacToeCpuOpponent(difficulty);
                break;
            case ONLINE_PLAYER:
                this.player2 = new InGamePlayer("Player 2","O",false);
                // this.opponent = new OnlineOpponent();
                break;
            case WITH_FRIEND:
            default:
                this.player2 = new InGamePlayer("Player 2","O",false);
                this.opponent = null;
                break;
        }
    }

    public String makeMove(Move move) {
        int row = move.row(); int col = move.col(); InGamePlayer playerWhoMoved = move.player();
        if (isGameOver || !board[row][col].isEmpty()) return null;

        board[row][col] = playerWhoMoved.symbol();
        gameLog.add(0, playerWhoMoved.name() + " placed " + playerWhoMoved.symbol() + " at [" + row + "," + col + "]");

        winningLine = engine.getWinningLine(board, row, col);

        if (winningLine != null) {
            isGameOver = true;
            statusMessage.set(playerWhoMoved.name()+ " Wins!");
            updateScore(playerWhoMoved.symbol());
            if (gameOverListener != null) {gameOverListener.onGameOver(player1, player2, playerWhoMoved);}

        } else if (engine.isBoardFull(board)) {
            isGameOver = true;
            statusMessage.set("It's a Draw!");
            if (gameOverListener != null) {gameOverListener.onGameOver(player1, player2, null);}
        } else {
            currentPlayer = (playerWhoMoved == player1) ? player2 : player1;
            statusMessage.set(currentPlayer.name()+"'s Turn");
        }
        if (moveListener != null) {moveListener.onMoveMade(move);}

        if (!isGameOver && opponent != null && !Objects.equals(currentPlayer.symbol(), "X")) {
            opponent.requestMove(board, (movement) -> {
                javafx.application.Platform.runLater(() -> makeMove(movement));
            });
        }
        System.out.println(playerWhoMoved.symbol());
        return playerWhoMoved.symbol();
    }


    private void updateScore(String winner) {
        if (winner.equals("X")) playerXScore.set(playerXScore.get() + 1);
        else playerOScore.set(playerOScore.get() + 1);
    }

    public StringProperty statusMessageProperty() { return statusMessage; }
    public IntegerProperty playerXScoreProperty() { return playerXScore; }
    public IntegerProperty playerOScoreProperty() { return playerOScore; }
    public ObservableList<String> getGameLog() { return gameLog; }

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

    public String getSymbolAt(int r, int c) {
        return board[r][c];
    }
    public InGamePlayer getCurrentPlayer() {
        return currentPlayer;
    }


}