package com.mycompany.xtremeo.client.model.common;

import com.mycompany.xtremeo.client.enums.PlayerStatus;
import com.mycompany.xtremeo.client.enums.Tier;

import java.util.Objects;

public record PlayerProfile(Player player, Score score, int elo) {
    private static final Score EMPTY_SCORE =
            new Score(0, 0, "UNKNOWN", 0, 0, 0, 0);

    public PlayerProfile {
        Objects.requireNonNull(player, "player must not be null");
        score = (score == null) ? EMPTY_SCORE : score;
    }

    public Tier tier() {
        return Tier.fromScore(elo);
    }

    public boolean isInGame() {
        return player().getStatus() == PlayerStatus.INGAME;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerProfile that = (PlayerProfile) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(player);
    }
}
