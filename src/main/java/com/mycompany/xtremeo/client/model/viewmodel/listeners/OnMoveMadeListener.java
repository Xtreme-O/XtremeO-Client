package com.mycompany.xtremeo.client.model.viewmodel.listeners;

import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.Move;

@FunctionalInterface
public interface OnMoveMadeListener {
    void onMoveMade(Move move);
}