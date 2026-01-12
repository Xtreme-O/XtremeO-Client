package com.mycompany.xtremeo.client.game;

import com.mycompany.xtremeo.client.adapter.TicTacToeBoardAdapter;
import com.mycompany.xtremeo.client.ai.*;
import com.mycompany.xtremeo.client.ai.evaluator.TicTacToeHeuristicProvider;
import com.mycompany.xtremeo.client.ai.strategies.BoardEvaluator;
import com.mycompany.xtremeo.client.ai.strategies.HeuristicProvider;
import com.mycompany.xtremeo.client.ai.evaluator.TicTacToeEvaluator;
import com.mycompany.xtremeo.client.model.game.Board;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.Move;

public class TicTacToeCpuOpponent implements GameOpponent {
    private final AIPlayerMoveProvider moveProvider;
    private final AIContext context;
    private static final int MAX_DEPTH = 9;

    public TicTacToeCpuOpponent(Difficulty difficulty, InGamePlayer humanPlayer, InGamePlayer aiPlayer) {
        HeuristicProvider heuristicProvider = new TicTacToeHeuristicProvider();

        MoveStrategy strategy = AIStrategyFactory.createStrategy(difficulty, heuristicProvider);

        BoardEvaluator evaluator = new TicTacToeEvaluator();

        Board emptyBoard = new TicTacToeBoardAdapter(new String[3][3]);

        this.context = new AIContext(emptyBoard, aiPlayer, humanPlayer, evaluator, MAX_DEPTH);
        this.moveProvider = new AIPlayerMoveProvider(context, strategy);
    }

    @Override
    public void requestMove(String[][] currentBoard, OnMoveDecisionCallback callback) {
        Board board = new TicTacToeBoardAdapter(currentBoard);
        context.setBoard(board);
        Move move = moveProvider.makeMove(board);

        if (move != null) {
            callback.onMoveDecided(move);// by mona
            return;
        }
        throw new IllegalStateException("No move found for CPU");
    }
}
