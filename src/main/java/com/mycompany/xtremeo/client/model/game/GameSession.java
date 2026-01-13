package com.mycompany.xtremeo.client.model.game;
import com.mycompany.xtremeo.client.game.GameOpponent;

public class GameSession {

    private final InGamePlayer localPlayer;
    private final InGamePlayer opponentPlayer;
    private final GameOpponent opponent;


    public GameSession(InGamePlayer localPlayer,
                       InGamePlayer opponentPlayer,
                       GameOpponent opponent) {
        this.localPlayer = localPlayer;
        this.opponentPlayer = opponentPlayer;
        this.opponent = opponent;
    }

    public InGamePlayer getLocalPlayer() {
        return localPlayer;
    }

    public InGamePlayer getOpponentPlayer() {
        return opponentPlayer;
    }

    public GameOpponent getOpponent() {
        return opponent;
    }
}
