package com.mycompany.xtremeo.client.model.lobby;

import com.mycompany.xtremeo.client.model.common.PlayerScore;

import java.util.List;

public record LobbyBody(List<LobbyPlayer> activeUsers, List<PlayerScore> playersScores) { }
