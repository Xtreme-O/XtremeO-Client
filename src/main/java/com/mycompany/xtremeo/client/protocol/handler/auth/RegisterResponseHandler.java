package com.mycompany.xtremeo.client.protocol.handler.auth;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class RegisterResponseHandler implements ResponseHandler<Player> {

    private static Consumer<Player> onRegisterResponse;

    public static void setOnRegisterResponseConsumer(Consumer<Player> consumer){
        onRegisterResponse = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<Player> envelope =
                GsonProvider.getGsonProvider().fromJson(
                        json,
                        new TypeToken<RequestEnvelope<Player>>(){}.getType()
                );

        Player player = envelope.getBody();
        onRegisterResponse.accept(player);
    }
}
