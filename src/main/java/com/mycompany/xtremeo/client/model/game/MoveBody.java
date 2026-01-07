package com.mycompany.xtremeo.client.model.game;

import com.mycompany.xtremeo.client.model.InGamePlayer;

public class MoveBody {
    InGamePlayer player;
    int row;
    int col;

    public MoveBody(InGamePlayer player, int row, int col) {
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public InGamePlayer getPlayer() {
        return player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
