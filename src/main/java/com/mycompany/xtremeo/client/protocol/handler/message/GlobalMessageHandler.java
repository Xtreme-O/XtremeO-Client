package com.mycompany.xtremeo.client.protocol.handler.message;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;
// TODO Change MessageBody by ChatMessageData
public class GlobalMessageHandler implements ResponseHandler<ChatMessageData> {

    private static Consumer<ChatMessageData> onMessageResponse;

    public static void setOnMessageResponse(Consumer<ChatMessageData> consumer){
        onMessageResponse = consumer;
    }

    @Override
    public void handle(String json) {
        RequestEnvelope<ChatMessageData> envelope =
                GsonProvider.getGsonProvider().fromJson(
                        json,
                        new TypeToken<RequestEnvelope<ChatMessageData>>(){}.getType()
                );

        ChatMessageData body = envelope.getBody();
        onMessageResponse.accept(body);
    }
}
