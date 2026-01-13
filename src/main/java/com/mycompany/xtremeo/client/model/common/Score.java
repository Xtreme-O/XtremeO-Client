package com.mycompany.xtremeo.client.model.common;

public record Score(
        int scoreId,
        int userId,
        String gameType,
        int wins,
        int losses,
        int draws,
        int longestStreak
) {
}