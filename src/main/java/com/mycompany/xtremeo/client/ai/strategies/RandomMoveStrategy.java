package com.mycompany.xtremeo.client.ai.strategies;

import java.util.List;
import java.util.Random;

import com.mycompany.xtremeo.client.ai.AIContext;
import com.mycompany.xtremeo.client.ai.MoveStrategy;
import com.mycompany.xtremeo.client.model.game.Cell;
import com.mycompany.xtremeo.client.model.game.Move;

public class RandomMoveStrategy implements MoveStrategy {


    @Override
    public Move chooseMove(AIContext context) {
        List<Cell> empty = context.getBoard().getEmptyCells();
        if (empty.isEmpty()) return null;

        Cell choice = empty.get(new Random().nextInt(empty.size()));
        return new Move(context.getAiPlayer(), choice.getRow(), choice.getCol());
    }
}
