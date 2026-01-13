package com.mycompany.xtremeo.client.protocol.handler.lobby;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class PlayerDisconnectedResponseHandler implements ResponseHandler<PlayerProfile> {
    private static Consumer<PlayerProfile> onPlayerDisconnected;
    public static void setOnPlayerDisconnected(Consumer<PlayerProfile> consumer) {
        onPlayerDisconnected = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<PlayerProfile> envelope = GsonProvider.getGsonProvider().fromJson(json,
                new TypeToken<RequestEnvelope<PlayerProfile>>() {
                }.getType());
        PlayerProfile body = envelope.getBody();
        onPlayerDisconnected.accept(body);
    }
}
