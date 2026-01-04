package com.mycompany.xtremeo.client.ai;

import com.mycompany.xtremeo.client.model.Move;

public interface MoveStrategy {
    Move chooseMove(AIContext context);
}
