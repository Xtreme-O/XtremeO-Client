package com.mycompany.xtremeo.client.ai.strategies;

import com.mycompany.xtremeo.client.ai.AIContext;
import com.mycompany.xtremeo.client.ai.MoveStrategy;
import com.mycompany.xtremeo.client.model.Board;
import com.mycompany.xtremeo.client.model.Cell;
import com.mycompany.xtremeo.client.model.InGamePlayer;
import com.mycompany.xtremeo.client.model.Move;

import java.util.List;

public class MinimaxStrategy implements MoveStrategy {

    private static final int WIN_SCORE = 10;

    @Override
    public Move chooseMove(AIContext context) {
        Board board = context.getBoard();
        InGamePlayer ai = context.getAiPlayer();

        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;

        for (Cell cell : board.getEmptyCells()) {
            cell.setSymbol(ai.symbol());
            int score = minimax(context, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            cell.setSymbol("");

            if (score > bestScore) {
                bestScore = score;
                bestMove = new Move(ai, cell.getRow(), cell.getCol());
            }
        }

        return bestMove;
    }


    private int minimax(AIContext context, int depth, boolean isAiTurn, int alpha, int beta) {
        Board board = context.getBoard();
        InGamePlayer ai = context.getAiPlayer();
        InGamePlayer opponent = context.getOpponent();
        var evaluator = context.getEvaluator();

        // Terminal state
        if (depth >= context.getMaxDepth()) return evaluator.evaluate(board, ai, opponent);
        if (evaluator.isWin(board, ai)) return WIN_SCORE - depth;
        if (evaluator.isWin(board, opponent)) return depth - WIN_SCORE;
        if (evaluator.isDraw(board)) return 0;

        List<Cell> emptyCells = board.getEmptyCells();
        if (isAiTurn) {
            int maxScore = Integer.MIN_VALUE;
            for (Cell cell : emptyCells) {
                cell.setSymbol(ai.symbol());
                int score = minimax(context, depth + 1, false, alpha, beta);
                cell.setSymbol("");

                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, maxScore);

                if (beta <= alpha) break;
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (Cell cell : emptyCells) {
                cell.setSymbol(opponent.symbol());
                int score = minimax(context, depth + 1, true, alpha, beta);
                cell.setSymbol("");

                minScore = Math.min(minScore, score);
                beta = Math.min(beta, minScore);

                if (beta <= alpha) break;
            }
            return minScore;
        }
    }
}
