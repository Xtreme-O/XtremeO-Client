package com.mycompany.xtremeo.client.model;

import java.util.List;

public interface Board {
    int getSize();
    boolean isCellEmpty(int row, int col);
    Cell getCell(int row, int col);
    List<Cell> getEmptyCells();
}