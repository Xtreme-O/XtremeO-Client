package com.mycompany.xtremeo.client.protocol.handler.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.model.common.Player;

import java.util.function.Consumer;

public class RegisterResponseHandler implements ResponseHandler<Player> {

    private static Consumer<Player> onRegisterResponse;

    public static void setOnRegisterResponseConsumer(Consumer<Player> consumer){
        onRegisterResponse = consumer;
    }

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<Player> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<Player>>(){}.getType()
                );

        Player player = envelope.getBody();
        onRegisterResponse.accept(player);
    }
}
