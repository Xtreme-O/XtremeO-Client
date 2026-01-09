package com.mycompany.xtremeo.client.model.recording;

import com.mycompany.xtremeo.client.model.game.*;

import java.util.List;

public class TicTacToeGameReplayer implements GameReplayer<Move> {

    private final List<MoveEntry> moves;
    private int currentIndex = 0;

    public TicTacToeGameReplayer(GameHistoryEntry historyEntry) {
        this.moves = historyEntry.moves();
    }

    @Override
    public boolean hasNextMove() {
        return currentIndex < moves.size();
    }

    @Override
    public Move nextMove() {
        if (!hasNextMove()) {
            throw new IllegalStateException("No more moves to replay");
        }
        return moves.get(currentIndex++).move();
    }

    @Override
    public void reset() {
        currentIndex = 0;
    }
    @Override
    public int totalMoves() {
        return moves.size();
    }

    @Override
    public int currentMoveIndex() {
        return currentIndex;
    }
}
