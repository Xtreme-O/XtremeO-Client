package com.mycompany.xtremeo.client.model.recording;

public interface GameReplayer<T> {

    boolean hasNextMove();

    T nextMove();

    void reset();

    int totalMoves();

    int currentMoveIndex();
}
