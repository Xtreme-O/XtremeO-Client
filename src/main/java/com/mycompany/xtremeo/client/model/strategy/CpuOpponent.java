package com.mycompany.xtremeo.client.model.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// دي انا بجرب بيها عادي امسحهوها
public class CpuOpponent implements GameOpponent {
    private Random random = new Random();

    @Override
    public void requestMove(String[][] currentBoard, OnMoveDecisionCallback callback) {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j].isEmpty()) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            int[] move = emptyCells.get(random.nextInt(emptyCells.size()));

            new Thread(() -> {
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                callback.onMoveDecided(move[0], move[1]);
            }).start();
        }
    }
}