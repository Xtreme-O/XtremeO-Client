package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.InviteConfirmationBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class InviteConfirmResponseHandler implements ResponseHandler<InviteConfirmationBody> {

    private static Consumer<InviteConfirmationBody> onInviteConfirmResponse;

    public static void setOnInviteConfirmResponse(Consumer<InviteConfirmationBody> consumer){
        onInviteConfirmResponse = consumer;
    }


    @Override
    public void handle(String json) {
        RequestEnvelope<InviteConfirmationBody> envelope = GsonProvider.getGsonProvider().fromJson(json,
                new TypeToken<RequestEnvelope<InviteConfirmationBody>>() {
                }.getType());
        InviteConfirmationBody confirmedBody = envelope.getBody();
        onInviteConfirmResponse.accept(confirmedBody);
    }
}
