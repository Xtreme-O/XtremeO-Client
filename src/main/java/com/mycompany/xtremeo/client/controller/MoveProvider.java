package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.Board;
import com.mycompany.xtremeo.client.model.Move;

public interface MoveProvider {
    Move makeMove(Board board);
}
