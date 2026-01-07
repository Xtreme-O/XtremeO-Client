package com.mycompany.xtremeo.client.model.auth;

import com.mycompany.xtremeo.client.model.common.Player;

public class AuthResponseBody {
    // if we change the AuthResponse body to add more data
    private Player player;

    public AuthResponseBody(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
