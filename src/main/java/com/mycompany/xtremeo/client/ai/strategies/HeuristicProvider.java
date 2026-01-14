package com.mycompany.xtremeo.client.ai.strategies;

import com.mycompany.xtremeo.client.model.game.Board;
import com.mycompany.xtremeo.client.model.game.Cell;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;

public interface HeuristicProvider {

    Cell chooseHeuristicMove(Board board, InGamePlayer aiPlayer, InGamePlayer opponent);
}