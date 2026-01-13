package com.mycompany.xtremeo.client.model.lobby;

import com.mycompany.xtremeo.client.model.common.PlayerProfile;

import java.util.List;

public record LobbyBody(List<PlayerProfile> activeUsers, List<PlayerProfile> playersScores) { }
