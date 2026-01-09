package com.mycompany.xtremeo.client.protocol.handler.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.listener.auth.RegisterUIListener;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.model.common.Player;

public class RegisterResponseHandler implements ResponseHandler<Player> {

    private final RegisterUIListener registerListener;

    public RegisterResponseHandler(RegisterUIListener registerListener) {
        this.registerListener = registerListener;
    }

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<Player> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<Player>>(){}.getType()
                );

        Player player = envelope.getBody();
        registerListener.onRegisterResponse(player);
    }
}
