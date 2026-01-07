package com.mycompany.xtremeo.client.model.game;

import java.time.LocalDateTime;
import java.util.List;

public record GameHistoryEntry(
        GameResult result,
        InGamePlayer player1,
        InGamePlayer player2,
        InGamePlayer winner,
        LocalDateTime time,
        List<MoveEntry> moves
) {}