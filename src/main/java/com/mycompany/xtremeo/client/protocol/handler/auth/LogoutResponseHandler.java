package com.mycompany.xtremeo.client.protocol.handler.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.auth.LogoutResponseBody;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;

import java.util.function.Consumer;

public class LogoutResponseHandler implements ResponseHandler<LogoutResponseBody> {

    private static Consumer<LogoutResponseBody> onLogoutResponse;

    public static void setOnLogoutResponseConsumer(Consumer<LogoutResponseBody> consumer){
        onLogoutResponse = consumer;
    }

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<LogoutResponseBody> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<LogoutResponseBody>>(){}.getType()
                );
        LogoutResponseBody username = envelope.getBody();
        onLogoutResponse.accept(username);
    }
}
