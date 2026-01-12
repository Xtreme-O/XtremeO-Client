package com.mycompany.xtremeo.client.model.viewmodel.listeners;

import com.mycompany.xtremeo.client.model.game.InGamePlayer;

@FunctionalInterface
public interface OnGameOverListener {
    void onGameOver(InGamePlayer player1, InGamePlayer player2, InGamePlayer winner);
}