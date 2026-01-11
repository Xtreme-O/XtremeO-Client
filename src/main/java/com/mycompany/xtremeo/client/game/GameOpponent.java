package com.mycompany.xtremeo.client.game;

import com.mycompany.xtremeo.client.model.game.Move;

public interface GameOpponent {
    void requestMove(String[][] currentBoard, OnMoveDecisionCallback callback);

    interface OnMoveDecisionCallback {
        void onMoveDecided(Move move);
    }
}

