package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.InviteConfirmedBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;

import java.util.function.Consumer;

public class InviteConfirmResponseHandler implements ResponseHandler<InviteConfirmedBody> {

    private static Consumer<InviteConfirmedBody> onInviteConfirmResponse;

    public static void setOnInviteConfirmResponse(Consumer<InviteConfirmedBody> consumer){
        onInviteConfirmResponse = consumer;
    }


    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<InviteConfirmedBody> envelope = gson.fromJson(json,
                new TypeToken<RequestEnvelope<InviteConfirmedBody>>() {
                }.getType());
        InviteConfirmedBody confirmedBody = envelope.getBody();
        onInviteConfirmResponse.accept(confirmedBody);
    }
}
