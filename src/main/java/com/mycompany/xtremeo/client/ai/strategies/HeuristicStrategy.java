package com.mycompany.xtremeo.client.ai.strategies;

import com.mycompany.xtremeo.client.ai.AIContext;
import com.mycompany.xtremeo.client.ai.MoveStrategy;
import com.mycompany.xtremeo.client.model.game.Board;
import com.mycompany.xtremeo.client.model.game.Cell;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.Move;

import java.util.List;
import java.util.Random;

public class HeuristicStrategy implements MoveStrategy {

    private final HeuristicProvider heuristicProvider;

    public HeuristicStrategy(HeuristicProvider heuristicProvider) {
        this.heuristicProvider = heuristicProvider;
    }

    @Override
    public Move chooseMove(AIContext context) {
        Board board = context.getBoard();
        InGamePlayer aiPlayer = context.getAiPlayer();
        InGamePlayer opponent = context.getOpponent();
        var evaluator = context.getEvaluator();

        Move winningMove = findWinningMove(board, aiPlayer, evaluator);
        if (winningMove != null) return winningMove;

        Move blockMove = findWinningMove(board, opponent, evaluator);
        if (blockMove != null) return new Move(aiPlayer, blockMove.row(), blockMove.col());

        Move heuristicMove = chooseHeuristicMove(board, aiPlayer, opponent);
        if (heuristicMove != null) return heuristicMove;

        return chooseRandomMove(board, aiPlayer);
    }


    private Move findWinningMove(Board board, InGamePlayer player, BoardEvaluator evaluator) {
        for (Cell cell : board.getEmptyCells()) {
            cell.setSymbol(player.symbol());
            if (evaluator.isWin(board, player)) {
                cell.setSymbol("");
                return new Move(player, cell.getRow(), cell.getCol());
            }
            cell.setSymbol("");
        }
        return null;
    }

    private Move chooseHeuristicMove(Board board, InGamePlayer aiPlayer, InGamePlayer opponent) {
        Cell heuristicCell = heuristicProvider.chooseHeuristicMove(board, aiPlayer, opponent);
        return heuristicCell == null ? null : new Move(aiPlayer, heuristicCell.getRow(), heuristicCell.getCol());
    }

    private Move chooseRandomMove(Board board, InGamePlayer aiPlayer) {
        List<Cell> empty = board.getEmptyCells();
        if (empty.isEmpty()) return null;
        Cell choice = empty.get(new Random().nextInt(empty.size()));
        return new Move(aiPlayer, choice.getRow(), choice.getCol());
    }
}
