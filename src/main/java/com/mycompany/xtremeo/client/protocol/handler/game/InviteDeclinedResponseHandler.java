package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.InviteConfirmationBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;

import java.util.function.Consumer;

public class InviteDeclinedResponseHandler implements ResponseHandler<InviteConfirmationBody> {

    private static Consumer<InviteConfirmationBody> onInviteDeclinedResponse;

    public static void setOnInviteDeclinedResponse(Consumer<InviteConfirmationBody> consumer){
        onInviteDeclinedResponse = consumer;
    }


    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<InviteConfirmationBody> envelope = gson.fromJson(json,
                new TypeToken<RequestEnvelope<InviteConfirmationBody>>() {
                }.getType());
        InviteConfirmationBody confirmedBody = envelope.getBody();
        onInviteDeclinedResponse.accept(confirmedBody);
    }
}
