package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.InviteBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class InviteResponseHandler implements ResponseHandler<InviteBody> {

    private static Consumer<InviteBody> onInviteResponse;

    public static void setOnInviteResponseConsumer(Consumer<InviteBody> consumer){
        onInviteResponse = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<InviteBody> envelope = GsonProvider.getGsonProvider().fromJson(json,
                new TypeToken<RequestEnvelope<InviteBody>>() {}.getType());

        InviteBody inviteBody = envelope.getBody();
        onInviteResponse.accept(inviteBody);
    }
}
