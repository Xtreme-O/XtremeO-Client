package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.InviteConfirmationResponseBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class InviteConfirmResponseHandler implements ResponseHandler<InviteConfirmationResponseBody> {

    private static Consumer<InviteConfirmationResponseBody> onInviteConfirmResponse;

    public static void setOnInviteConfirmResponse(Consumer<InviteConfirmationResponseBody> consumer){
        onInviteConfirmResponse = consumer;
    }


    @Override
    public void handle(String json) {
        RequestEnvelope<InviteConfirmationResponseBody> envelope = GsonProvider.getGsonProvider().fromJson(json,
                new TypeToken<RequestEnvelope<InviteConfirmationResponseBody>>() {
                }.getType());
        InviteConfirmationResponseBody confirmedBody = envelope.getBody();
        onInviteConfirmResponse.accept(confirmedBody);
    }
}
