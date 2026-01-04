package com.mycompany.xtremeo.client.model;

public class Cell {
    private final int row;
    private final int col;
    private String symbol = "";

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public boolean isEmpty() { return symbol == null || symbol.isEmpty(); }
}

