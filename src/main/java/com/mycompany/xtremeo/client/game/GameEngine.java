package com.mycompany.xtremeo.client.game;

public class GameEngine {

    public int[][] getWinningLine(String[][] board, int r, int c) {
        String s = board[r][c];
        if (s.isEmpty()) return null;

        if (board[r][0].equals(s) && board[r][1].equals(s) && board[r][2].equals(s))
            return new int[][]{{r, 0}, {r, 1}, {r, 2}};

        if (board[0][c].equals(s) && board[1][c].equals(s) && board[2][c].equals(s))
            return new int[][]{{0, c}, {1, c}, {2, c}};

        if (r == c && board[0][0].equals(s) && board[1][1].equals(s) && board[2][2].equals(s))
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};

        if (r + c == 2 && board[0][2].equals(s) && board[1][1].equals(s) && board[2][0].equals(s))
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};

        return null;
    }

    public boolean isBoardFull(String[][] board) {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell.isEmpty()) return false;
            }
        }
        return true;
    }
}

