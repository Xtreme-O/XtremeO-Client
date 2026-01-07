package com.mycompany.xtremeo.client.service.recording;

import com.mycompany.xtremeo.client.model.game.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeGameRecorder implements MoveRecorder {

    private final List<MoveEntry> recordedMoves;

    public TicTacToeGameRecorder() {
        recordedMoves = new ArrayList<>();
    }

    @Override
    public void recordMove(Move move) {
        recordedMoves.add(new MoveEntry(move, recordedMoves.size() + 1));
    }

    public List<MoveEntry> getRecordedEntries() {
        return recordedMoves;
    }


    public GameHistoryEntry toGameHistoryEntry(
            InGamePlayer player1,
            InGamePlayer player2,
            InGamePlayer winner,
            GameResult result
    ) {
        return new GameHistoryEntry(
                result,
                player1,
                player2,
                winner,
                LocalDateTime.now(),
                new ArrayList<>(recordedMoves)
        );
    }
}
