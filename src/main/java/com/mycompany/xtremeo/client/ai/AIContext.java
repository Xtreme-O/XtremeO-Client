package com.mycompany.xtremeo.client.ai;

import com.mycompany.xtremeo.client.ai.strategies.BoardEvaluator;
import com.mycompany.xtremeo.client.model.game.Board;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;

public class AIContext {
    private Board board;
    private final InGamePlayer aiPlayer;
    private final InGamePlayer opponent;
    private final BoardEvaluator evaluator;
    private final int maxDepth;
    public AIContext(Board board, InGamePlayer aiPlayer, InGamePlayer opponent,
                     BoardEvaluator evaluator,
                     int maxDepth) {
        this.board = board;
        this.aiPlayer = aiPlayer;
        this.opponent = opponent;
        this.evaluator = evaluator;
        this.maxDepth = maxDepth;
    }

    public Board getBoard() { return board; }

    public void setBoard(Board board) { this.board = board; }
    public InGamePlayer getAiPlayer() { return aiPlayer; }
    public InGamePlayer getOpponent() { return opponent; }
    public BoardEvaluator getEvaluator() { return evaluator; }
    public int getMaxDepth() {return maxDepth;}
}

