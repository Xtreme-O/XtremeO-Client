package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.game.Board;
import com.mycompany.xtremeo.client.model.game.Move;

public interface MoveProvider {
    Move makeMove(Board board);
}
