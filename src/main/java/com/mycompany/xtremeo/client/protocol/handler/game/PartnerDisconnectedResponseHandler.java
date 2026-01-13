package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.PartnerDisconnectedBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class PartnerDisconnectedResponseHandler implements ResponseHandler<PartnerDisconnectedBody> {

    private static Consumer<PartnerDisconnectedBody> onPartnerDisconnected;

    public static void setOnPartnerDisconnected(Consumer<PartnerDisconnectedBody> consumer) {
        onPartnerDisconnected = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<PartnerDisconnectedBody> envelope = GsonProvider.getGsonProvider().fromJson(json,
                new TypeToken<RequestEnvelope<PartnerDisconnectedBody>>() {
                }.getType());
        PartnerDisconnectedBody body = envelope.getBody();
        onPartnerDisconnected.accept(body);
    }
}
