package com.mycompany.xtremeo.client.model.game;

import com.mycompany.xtremeo.client.enums.GameState;

public record SessionMessageBody(SessionMove move, GameState state) { }
