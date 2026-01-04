package com.mycompany.xtremeo.client.ai.evaluator;

import com.mycompany.xtremeo.client.model.Board;
import com.mycompany.xtremeo.client.model.Cell;
import com.mycompany.xtremeo.client.model.InGamePlayer;
import com.mycompany.xtremeo.client.ai.strategies.HeuristicProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicTacToeHeuristicProvider implements HeuristicProvider {

    @Override
    public Cell chooseHeuristicMove(Board board, InGamePlayer aiPlayer, InGamePlayer opponent) {
        Cell center = chooseCenter(board);
        if (center != null) return center;

        Cell oppositeCorner = chooseOppositeCorner(board, opponent);
        if (oppositeCorner != null) return oppositeCorner;

        Cell corner = chooseCorner(board);
        if (corner != null) return corner;

        return chooseEdge(board);
    }

    private Cell chooseCenter(Board board) {
        int size = board.getSize();
        int centerRow = size / 2;
        int centerCol = size / 2;
        if (board.isCellEmpty(centerRow, centerCol)) {
            return board.getCell(centerRow, centerCol);
        }
        return null;
    }

    private Cell chooseOppositeCorner(Board board, InGamePlayer opponent) {
        List<Cell> corners = getCorners(board);
        for (Cell corner : corners) {
            int oppRow = board.getSize() - 1 - corner.getRow();
            int oppCol = board.getSize() - 1 - corner.getCol();
            Cell opposite = board.getCell(oppRow, oppCol);

            if (!corner.isEmpty() && corner.getSymbol().equals(opponent.symbol()) && opposite.isEmpty()) {
                return opposite;
            }
        }
        return null;
    }

    private Cell chooseCorner(Board board) {
        List<Cell> emptyCorners = new ArrayList<>();
        for (Cell corner : getCorners(board)) {
            if (corner.isEmpty()) emptyCorners.add(corner);
        }

        if (!emptyCorners.isEmpty()) {
            Collections.shuffle(emptyCorners);
            return emptyCorners.getFirst();
        }
        return null;
    }

    private Cell chooseEdge(Board board) {
        List<Cell> emptyEdges = new ArrayList<>();
        int size = board.getSize();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Cell cell = board.getCell(r, c);
                if (cell.isEmpty() && !isCorner(cell, size) && !isCenter(cell, size)) {
                    emptyEdges.add(cell);
                }
            }
        }

        if (!emptyEdges.isEmpty()) {
            Collections.shuffle(emptyEdges);
            return emptyEdges.getFirst();
        }
        return null;
    }

    private List<Cell> getCorners(Board board) {
        int size = board.getSize();
        return List.of(
                board.getCell(0, 0),
                board.getCell(0, size - 1),
                board.getCell(size - 1, 0),
                board.getCell(size - 1, size - 1)
        );
    }

    private boolean isCorner(Cell cell, int size) {
        return (cell.getRow() == 0 || cell.getRow() == size - 1) &&
                (cell.getCol() == 0 || cell.getCol() == size - 1);
    }

    private boolean isCenter(Cell cell, int size) {
        return cell.getRow() == size / 2 && cell.getCol() == size / 2;
    }
}
