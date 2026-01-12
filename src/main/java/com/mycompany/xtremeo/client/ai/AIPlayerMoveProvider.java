package com.mycompany.xtremeo.client.ai;

import com.mycompany.xtremeo.client.controller.MoveProvider;
import com.mycompany.xtremeo.client.model.game.Board;
import com.mycompany.xtremeo.client.model.game.Move;

public class AIPlayerMoveProvider implements MoveProvider {

    AIContext context;
    MoveStrategy strategy;

    public AIPlayerMoveProvider(AIContext context , MoveStrategy strategy) {
        this.context = context;
        this.strategy = strategy;
    }

    @Override
    public Move makeMove(Board board) {

        return strategy.chooseMove(context);
    }
}
