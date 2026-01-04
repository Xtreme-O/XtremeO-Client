package com.mycompany.xtremeo.client.adapter;

import com.mycompany.xtremeo.client.model.Cell;

public class BoardConverter {

    public static Cell[][] toCells(String[][] boardArray) {
        int size = boardArray.length;
        Cell[][] cells = new Cell[size][size];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Cell cell = new Cell(r, c);
                if (boardArray[r][c] != null) {
                    cell.setSymbol(boardArray[r][c]);
                }
                cells[r][c] = cell;
            }
        }
        return cells;
    }
}