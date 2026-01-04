package com.mycompany.xtremeo.client.model;

import java.util.List;

public class GameState {
    private Board board;
    private InGamePlayer currentPlayer;
    private List<Move> movesHistory;

    public GameState(Board board) {}

    public Board getBoard() { return null; }
    public InGamePlayer getCurrentPlayer() { return null; }
    public void addMove(Move move) {}
}
