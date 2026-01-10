package com.mycompany.xtremeo.client.model.lobby;

import com.mycompany.xtremeo.client.model.common.Player;

public record ChatMessageData(Player sender, String message, String time) {}
