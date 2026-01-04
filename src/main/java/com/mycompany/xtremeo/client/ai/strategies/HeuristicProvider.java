package com.mycompany.xtremeo.client.ai.strategies;

import com.mycompany.xtremeo.client.model.Board;
import com.mycompany.xtremeo.client.model.Cell;
import com.mycompany.xtremeo.client.model.InGamePlayer;

public interface HeuristicProvider {

    Cell chooseHeuristicMove(Board board, InGamePlayer aiPlayer, InGamePlayer opponent);
}