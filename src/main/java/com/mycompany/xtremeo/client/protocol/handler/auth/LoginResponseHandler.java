package com.mycompany.xtremeo.client.protocol.handler.auth;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class LoginResponseHandler implements ResponseHandler<Player> {

    private static Consumer<PlayerProfile> onLoginResponse;

    public static void setOnLoginResponseConsumer(Consumer<PlayerProfile> consumer){
        onLoginResponse = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<PlayerProfile> envelope =
                GsonProvider.getGsonProvider().fromJson(
                        json,
                        new TypeToken<RequestEnvelope<PlayerProfile>>(){}.getType()
                );
        PlayerProfile player = envelope.getBody();
        onLoginResponse.accept(player);
    }
}
