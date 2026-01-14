package com.mycompany.xtremeo.client.game;

public class GameEngine {

    public int[][] getWinningLine(String[][] board, int r, int c) {
        String s = board[r][c];
        if (s == null || s.isEmpty()) return null;

        if (isCellEqual(board, r, 0, s) && isCellEqual(board, r, 1, s) && isCellEqual(board, r, 2, s))
            return new int[][]{{r, 0}, {r, 1}, {r, 2}};

        if (isCellEqual(board, 0, c, s) && isCellEqual(board, 1, c, s) && isCellEqual(board, 2, c, s))
            return new int[][]{{0, c}, {1, c}, {2, c}};

        if (r == c && isCellEqual(board, 0, 0, s) && isCellEqual(board, 1, 1, s) && isCellEqual(board, 2, 2, s))
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};

        if (r + c == 2 && isCellEqual(board, 0, 2, s) && isCellEqual(board, 1, 1, s) && isCellEqual(board, 2, 0, s))
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};

        return null;
    }

    private boolean isCellEqual(String[][] board, int row, int col, String symbol) {
        String cell = board[row][col];
        return cell != null && cell.equals(symbol);
    }

    public boolean isBoardFull(String[][] board) {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell == null || cell.isEmpty()) return false;
            }
        }
        return true;
    }
}

