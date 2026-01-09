package com.mycompany.xtremeo.client.listener.game;

import com.mycompany.xtremeo.client.model.game.Move;

public interface MoveUIListener {
    void onMoveReceived(Move move);
}