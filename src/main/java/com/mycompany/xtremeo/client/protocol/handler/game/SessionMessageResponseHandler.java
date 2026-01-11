package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.SessionMessageBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;

import java.util.function.Consumer;

public class SessionMessageResponseHandler implements ResponseHandler<SessionMessageBody> {

    private static Consumer<SessionMessageBody> onSessionMessageReceived;

    public static void setOnSessionMessageReceived(Consumer<SessionMessageBody> consumer) {
        onSessionMessageReceived = consumer;
    }

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<SessionMessageBody> envelope = gson.fromJson(json,
                new TypeToken<RequestEnvelope<SessionMessageBody>>() {
                }.getType());
        SessionMessageBody messageBody = envelope.getBody();
        onSessionMessageReceived.accept(messageBody);
    }
}
