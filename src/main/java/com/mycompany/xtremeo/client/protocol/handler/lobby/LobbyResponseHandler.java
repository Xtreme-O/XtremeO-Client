package com.mycompany.xtremeo.client.protocol.handler.lobby;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.lobby.LobbyBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class LobbyResponseHandler implements ResponseHandler<LobbyBody> {
    private static Consumer<LobbyBody> onLobbyLoad;

    public static void setOnLobbyLoad(Consumer<LobbyBody> consumer) {
        onLobbyLoad = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<LobbyBody> envelope = GsonProvider.getGsonProvider().fromJson(json,
                new TypeToken<RequestEnvelope<LobbyBody>>() {
                }.getType());
        LobbyBody body = envelope.getBody();
        onLobbyLoad.accept(body);
    }
}
