package com.mycompany.xtremeo.client.model.recording;

import com.mycompany.xtremeo.client.model.game.*;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeGameRecorder implements GameRecorder<Move> {

    private final List<MoveEntry> recordedMoves = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    public void recordMove(Move move) {
        recordedMoves.add(new MoveEntry(move, currentIndex++));
    }

    @Override
    public List<Move> getRecordedMoves() {
        return recordedMoves.stream().map(MoveEntry::move).toList();
    }


    @Override
    public void reset() {
        recordedMoves.clear();
    }
    public List<MoveEntry> getEntries() {
        return new ArrayList<>(recordedMoves);
    }
}