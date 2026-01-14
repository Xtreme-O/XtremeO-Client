package com.mycompany.xtremeo.client.model.recording;


import java.util.List;

public interface GameRecorder<M> {
    void recordMove(M move);
    List<M> getRecordedMoves();
    void reset();
}