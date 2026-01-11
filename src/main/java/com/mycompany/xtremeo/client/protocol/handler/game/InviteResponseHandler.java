package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.InviteBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;

import java.util.function.Consumer;

public class InviteResponseHandler implements ResponseHandler<InviteBody> {

    private static Consumer<InviteBody> onInviteResponse;

    public static void setOnInviteResponseConsumer(Consumer<InviteBody> consumer){
        onInviteResponse = consumer;
    }

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<InviteBody> envelope = gson.fromJson(json,
                new TypeToken<RequestEnvelope<InviteBody>>() {}.getType());

        InviteBody inviteBody = envelope.getBody();
        onInviteResponse.accept(inviteBody);
    }
}
