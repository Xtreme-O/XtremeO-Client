package com.mycompany.xtremeo.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.xtremeo.client.model.Board;
import com.mycompany.xtremeo.client.model.Cell;

public class TicTacToeBoardAdapter implements Board {

    private final Cell[][] board;

    public TicTacToeBoardAdapter(String[][] board) {
        this.board = BoardConverter.toCells(board);
    }

    @Override
    public int getSize() {
        return board.length;
    }

    @Override
    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    @Override
    public List<Cell> getEmptyCells() {
        List<Cell> emptyCells = new ArrayList<>();
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                if (cell == null || cell.isEmpty()) {
                    emptyCells.add(cell);
                }
            }
        }
        return emptyCells;
    }


    @Override
    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == null || board[row][col].isEmpty();
    }

}