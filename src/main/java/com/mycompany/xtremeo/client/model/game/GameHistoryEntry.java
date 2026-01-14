package com.mycompany.xtremeo.client.model.game;

import com.mycompany.xtremeo.client.ai.Difficulty;

import java.time.LocalDateTime;
import java.util.List;

public record GameHistoryEntry(
        GameResult result,
        InGamePlayer player1,
        InGamePlayer player2,
        InGamePlayer winner,
        LocalDateTime time,
        List<MoveEntry> moves,
        Difficulty difficulty,
        GameMode gameMode
) {}