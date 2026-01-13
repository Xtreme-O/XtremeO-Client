package com.mycompany.xtremeo.client.model.game;

public record InGameChatMessage(
        InGamePlayer sender,
        String message,
        String time,
        boolean isLocalPlayer
) {}
