package com.mycompany.xtremeo.client.ai;

import com.mycompany.xtremeo.client.ai.strategies.HeuristicProvider;
import com.mycompany.xtremeo.client.ai.strategies.HeuristicStrategy;
import com.mycompany.xtremeo.client.ai.strategies.MinimaxStrategy;
import com.mycompany.xtremeo.client.ai.strategies.RandomMoveStrategy;

public class AIStrategyFactory {

        public static MoveStrategy createStrategy(Difficulty difficulty,
                                                  HeuristicProvider heuristicProvider) {
            return switch (difficulty) {
                case EASY -> new RandomMoveStrategy();
                case MEDIUM -> new HeuristicStrategy(heuristicProvider);
                case HARD -> new MinimaxStrategy();
                case NONE -> throw new IllegalArgumentException("Cannot create AI strategy for NONE difficulty");
            };
    }
}
