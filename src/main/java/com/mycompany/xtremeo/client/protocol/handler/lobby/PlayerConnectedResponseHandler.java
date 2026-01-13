package com.mycompany.xtremeo.client.protocol.handler.lobby;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class PlayerConnectedResponseHandler implements ResponseHandler<PlayerProfile> {
    private static Consumer<PlayerProfile> onPlayerConnected;
    public static void setOnPlayerConnected(Consumer<PlayerProfile> consumer) {
        onPlayerConnected = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<PlayerProfile> envelope = GsonProvider.getGsonProvider().fromJson(json,
                new TypeToken<RequestEnvelope<PlayerProfile>>() {
                }.getType());
        PlayerProfile body = envelope.getBody();
        onPlayerConnected.accept(body);
    }
}
