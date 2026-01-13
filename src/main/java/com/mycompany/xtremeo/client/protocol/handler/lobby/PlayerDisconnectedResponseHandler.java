package com.mycompany.xtremeo.client.protocol.handler.lobby;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.common.PlayerScore;
import com.mycompany.xtremeo.client.model.lobby.LobbyBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class PlayerDisconnectedResponseHandler implements ResponseHandler<PlayerScore> {
    private static Consumer<PlayerScore> onPlayerDisconnected;
    public static void setOnPlayerDisconnected(Consumer<PlayerScore> consumer) {
        onPlayerDisconnected = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<PlayerScore> envelope = GsonProvider.getGsonProvider().fromJson(json,
                new TypeToken<RequestEnvelope<PlayerScore>>() {
                }.getType());
        PlayerScore body = envelope.getBody();
        onPlayerDisconnected.accept(body);
    }
}
