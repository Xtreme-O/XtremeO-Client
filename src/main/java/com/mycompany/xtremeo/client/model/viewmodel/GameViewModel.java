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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameViewModel {

    public interface OnMoveMadeListener {
        void onMoveMade(int r, int c, String symbol);
    }
    private OnMoveMadeListener moveListener;

    private GameOpponent opponent;
    private final GameEngine engine = new GameEngine();
    private String[][] board = new String[3][3];
    private boolean isXTurn = true;
    private boolean isGameOver = false;

    private final StringProperty statusMessage = new SimpleStringProperty("Player X's Turn");
    private final IntegerProperty playerXScore = new SimpleIntegerProperty(0);
    private final IntegerProperty playerOScore = new SimpleIntegerProperty(0);

    private final ObservableList<String> gameLog = FXCollections.observableArrayList();
    private int[][] winningLine = null;
    public GameViewModel() {
        resetBoard();
    }

    public void setOnMoveMadeListener(OnMoveMadeListener listener) {
        this.moveListener = listener;
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
        isXTurn = true;
        isGameOver = false;
        winningLine = null;
        statusMessage.set("Player X's Turn");
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
                this.opponent = new TicTacToeCpuOpponent(difficulty);
                // test online move response
//                this.opponent = OnlineOpponent.getInstance();
                break;
            case MULTIPLAYER:
                // this.opponent = new OnlineOpponent();
                break;
            case WITH_FRIEND:
            default:
                this.opponent = null;
                break;
        }
    }

    public String makeMove(int row, int col) {
        if (isGameOver || !board[row][col].isEmpty()) return null;

        String currentSymbol = isXTurn ? "X" : "O";
        board[row][col] = currentSymbol;
        gameLog.add(0,"Player " + currentSymbol + " placed in " + row + "," + col);

        winningLine = engine.getWinningLine(board, row, col);
        if (winningLine != null) {
            isGameOver = true;
            statusMessage.set("Player " + currentSymbol + " Wins!");
            updateScore(currentSymbol);
        } else if (engine.isBoardFull(board)) {
            isGameOver = true;
            statusMessage.set("It's a Draw!");
        } else {
            isXTurn = !isXTurn;
            statusMessage.set("Player " + (isXTurn ? "X" : "O") + "'s Turn");
        }

        if (moveListener != null) {moveListener.onMoveMade(row, col, currentSymbol);}
        if (!isGameOver && opponent != null && !isXTurn) {
            opponent.requestMove(board, (r, c) -> {
                javafx.application.Platform.runLater(() -> makeMove(r, c));
            });
        }
        return currentSymbol;
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
        return isXTurn ? "X" : "O";
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


}