package com.mycompany.xtremeo.client.service.recording;

import com.mycompany.xtremeo.client.model.game.*;

import java.util.List;

public class TicTacToeGameReplayer {

    private final List<MoveEntry> moves;
    private int currentIndex = 0;

    public TicTacToeGameReplayer(GameHistoryEntry historyEntry) {
        this.moves = historyEntry.moves();
    }

    public boolean hasNextMove() {
        return currentIndex < moves.size();
    }

    public Move nextMove() {
        if (!hasNextMove()) {
            throw new IllegalStateException("No more moves to replay");
        }
        return moves.get(currentIndex++).move();
    }


    public void reset() {
        currentIndex = 0;
    }


    public List<Move> getRemainingMoves() {
        return moves.subList(currentIndex, moves.size()).stream()
                .map(MoveEntry::move)
                .toList();
    }


    public int totalMoves() {
        return moves.size();
    }

    public int currentMoveIndex() {
        return currentIndex;
    }
}
