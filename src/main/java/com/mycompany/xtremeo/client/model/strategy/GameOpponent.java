package com.mycompany.xtremeo.client.model.strategy;

public interface GameOpponent {
    void requestMove(String[][] currentBoard, OnMoveDecisionCallback callback);

    interface OnMoveDecisionCallback {
        void onMoveDecided(int row, int col);
    }
}