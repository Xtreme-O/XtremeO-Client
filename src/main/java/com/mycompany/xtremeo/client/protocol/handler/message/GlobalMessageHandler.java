package com.mycompany.xtremeo.client.protocol.handler.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;

import java.util.function.Consumer;
// TODO Change MessageBody by ChatMessageData
public class GlobalMessageHandler implements ResponseHandler<ChatMessageData> {

    private static Consumer<ChatMessageData> onMessageResponse;

    public static void setOnMessageResponse(Consumer<ChatMessageData> consumer){
        onMessageResponse = consumer;
    }

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<ChatMessageData> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<ChatMessageData>>(){}.getType()
                );

        ChatMessageData body = envelope.getBody();
        onMessageResponse.accept(body);
    }
}
