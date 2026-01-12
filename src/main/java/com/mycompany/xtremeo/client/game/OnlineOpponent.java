package com.mycompany.xtremeo.client.game;

import com.mycompany.xtremeo.client.model.game.Move;

public class OnlineOpponent implements GameOpponent {

    private static OnlineOpponent opponent;
    private OnMoveDecisionCallback pendingCallback;

    private OnlineOpponent() {}

    public static synchronized OnlineOpponent getInstance() {
        if (opponent == null) {
            opponent = new OnlineOpponent();
        }
        return opponent;
    }

    @Override
    public synchronized void requestMove(String[][] board, OnMoveDecisionCallback callback) {
        this.pendingCallback = callback;
        System.out.println("Waiting for server move...");
    }

    public synchronized void onMoveReceived(Move move) {
        System.out.println("Received move from server: " + move);
        if (pendingCallback != null) {
            pendingCallback.onMoveDecided(move);
            pendingCallback = null;
        }
    }
}
