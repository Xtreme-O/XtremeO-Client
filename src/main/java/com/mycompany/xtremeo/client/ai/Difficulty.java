package com.mycompany.xtremeo.client.ai;

import java.util.Arrays;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD,
    NONE;

    public static Difficulty fromString(String difficulty) {
        return Arrays.stream(Difficulty.values())
                .filter(d -> d.name().equalsIgnoreCase(difficulty))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown difficulty: " + difficulty));
    }
}
