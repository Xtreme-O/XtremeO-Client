package com.mycompany.xtremeo.client.ai;

import com.mycompany.xtremeo.client.model.game.Move;

public interface MoveStrategy {
    Move chooseMove(AIContext context);
}
