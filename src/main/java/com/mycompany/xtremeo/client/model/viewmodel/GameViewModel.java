/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.model.viewmodel;

import com.mycompany.xtremeo.client.model.strategy.CpuOpponent;
import com.mycompany.xtremeo.client.model.strategy.GameMode;
import com.mycompany.xtremeo.client.model.strategy.GameOpponent;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameViewModel {
    private GameOpponent opponent;
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

    public  void setGameMode(GameMode mode){
        this.isGameOver= false;
        resetBoard();
        switch (mode) {
            case WITH_CPU:
                this.opponent = new CpuOpponent();
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

        gameLog.add("Player " + currentSymbol + " placed in " + row + "," + col);

        if (checkWin(row, col)) {
            isGameOver = true;
            statusMessage.set("Player " + currentSymbol + " Wins!");
            updateScore(currentSymbol);
        } else if (isBoardFull()) {
            isGameOver = true;
            statusMessage.set("It's a Draw!");
        } else {
            isXTurn = !isXTurn;
            statusMessage.set("Player " + (isXTurn ? "X" : "O") + "'s Turn");
        }

        if (!isGameOver && opponent != null && !isXTurn) {
            opponent.requestMove(board, (r, c) -> {
                javafx.application.Platform.runLater(() -> makeMove(r, c));
            });
        }

        return currentSymbol;
    }

    private boolean checkWin(int r, int c) {
        String s = board[r][c];

        if (board[r][0].equals(s) && board[r][1].equals(s) && board[r][2].equals(s)) {
            winningLine = new int[][]{{r, 0}, {r, 1}, {r, 2}};
            return true;
        }
        if (board[0][c].equals(s) && board[1][c].equals(s) && board[2][c].equals(s)) {
            winningLine = new int[][]{{0, c}, {1, c}, {2, c}};
            return true;
        }
        if (r == c && board[0][0].equals(s) && board[1][1].equals(s) && board[2][2].equals(s)) {
            winningLine = new int[][]{{0, 0}, {1, 1}, {2, 2}};
            return true;
        }
        if (r + c == 2 && board[0][2].equals(s) && board[1][1].equals(s) && board[2][0].equals(s)) {
            winningLine = new int[][]{{0, 2}, {1, 1}, {2, 0}};
            return true;
        }
        return false;
    }


    private boolean isBoardFull() {
        for (String[] row : board)
            for (String cell : row) if (cell.isEmpty()) return false;
        return true;
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


}