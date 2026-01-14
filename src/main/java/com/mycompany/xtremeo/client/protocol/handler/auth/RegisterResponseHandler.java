package com.mycompany.xtremeo.client.protocol.handler.auth;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class RegisterResponseHandler implements ResponseHandler<PlayerProfile> {

    private static Consumer<PlayerProfile> onRegisterResponse;

    public static void setOnRegisterResponseConsumer(Consumer<PlayerProfile> consumer){
        onRegisterResponse = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<PlayerProfile> envelope =
                GsonProvider.getGsonProvider().fromJson(
                        json,
                        new TypeToken<RequestEnvelope<PlayerProfile>>(){}.getType()
                );

        PlayerProfile player = envelope.getBody();
        onRegisterResponse.accept(player);
    }
}
