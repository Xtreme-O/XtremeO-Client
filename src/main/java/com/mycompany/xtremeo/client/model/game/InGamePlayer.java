package com.mycompany.xtremeo.client.model.game;

import com.mycompany.xtremeo.client.model.common.Player;

import java.util.Objects;

public record InGamePlayer(Player player, String symbol) {


    public static InGamePlayer cpuPlayer() {
        return new InGamePlayer(Player.fromUsername("CPU"), "O");
    }


    public static InGamePlayer localOfflinePlayer() {
        return new InGamePlayer(Player.fromUsername("Player 1"), "X");
    }


    public static InGamePlayer opponentOfflinePlayer() {
        return new InGamePlayer(Player.fromUsername("Player 2"), "O");
    }

    public String name() {
        if (player != null) {
            return player.getUsername();
        }
        return switch (symbol) {
            case "X" -> "Player 1";
            case "O" -> "Player 2";
            default -> "Unknown Player";
        };
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InGamePlayer that = (InGamePlayer) o;
        return Objects.equals(player, that.player) && Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, symbol);
    }

    

}