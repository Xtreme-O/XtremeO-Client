package com.mycompany.xtremeo.client.ai.evaluator;

import com.mycompany.xtremeo.client.model.game.Board;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.ai.strategies.BoardEvaluator;

public class TicTacToeEvaluator implements BoardEvaluator {

    @Override
    public int evaluate(Board board, InGamePlayer aiPlayer, InGamePlayer opponent) {
        if (isWin(board, aiPlayer)) return 10;
        if (isWin(board, opponent)) return -10;
        return 0;
    }

    @Override
    public boolean isWin(Board board, InGamePlayer player) {
        return checkRows(board, player) ||
                checkColumns(board, player) ||
                checkDiagonals(board, player);
    }

    @Override
    public boolean isDraw(Board board) {
        return board.getEmptyCells().isEmpty();
    }


    private boolean checkRows(Board board, InGamePlayer player) {
        int size = board.getSize();
        String symbol = player.symbol();
        for (int r = 0; r < size; r++) {
            if (checkLine(board, r, 0, 0, 1, symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns(Board board, InGamePlayer player) {
        int size = board.getSize();
        String symbol = player.symbol();
        for (int c = 0; c < size; c++) {
            if (checkLine(board, 0, c, 1, 0, symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(Board board, InGamePlayer player) {
        int size = board.getSize();
        String symbol = player.symbol();
        return checkLine(board, 0, 0, 1, 1, symbol) ||
                checkLine(board, 0, size - 1, 1, -1, symbol);
    }

    private boolean checkLine(Board board, int startRow, int startCol, int rowInc, int colInc, String symbol) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            if (!board.getCell(startRow + i * rowInc, startCol + i * colInc).getSymbol().equals(symbol)) {
                return false;
            }
        }
        return true;
    }
}
