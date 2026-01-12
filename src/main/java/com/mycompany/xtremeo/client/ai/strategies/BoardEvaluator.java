package com.mycompany.xtremeo.client.ai.strategies;

import com.mycompany.xtremeo.client.model.game.Board;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;

public interface BoardEvaluator {
    int evaluate(Board board, InGamePlayer aiPlayer, InGamePlayer opponent); // +10/-10 for win/loss
    boolean isWin(Board board, InGamePlayer player);
    boolean isDraw(Board board);
}
