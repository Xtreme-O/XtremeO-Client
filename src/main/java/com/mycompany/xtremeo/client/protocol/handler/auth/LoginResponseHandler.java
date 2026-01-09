package com.mycompany.xtremeo.client.protocol.handler.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.model.common.Player;
import javafx.application.Platform;

public class LoginResponseHandler implements ResponseHandler<Player> {

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<Player> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<Player>>(){}.getType()
                );
        Player player = envelope.getBody();
        System.out.println("Created at: "+ player.getCreatedAt());

    }
}
