package com.mycompany.xtremeo.client.model.lobby;

import com.mycompany.xtremeo.client.model.common.Player;

import java.io.InvalidObjectException;

public record TopPlayerData(int rank, String name, String avatarUrl, int score) {

    public static TopPlayerData fromPlayer(Player player) throws Exception {
        // TODO : CREATE A STORE DTO AND EXTRACT THIS DATA FROM IT
        throw new InvalidObjectException("Cannot create TopPlayerData");
    }

}