package com.mycompany.xtremeo.client.protocol.handler.message;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.common.MessageBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class InGameMessageHandler implements ResponseHandler<MessageBody> {
    private static Consumer<MessageBody> onMessageResponse;

    public static void setOnMessageResponse(Consumer<MessageBody> consumer){
        onMessageResponse = consumer;
    }
    @Override
    public void handle(String json) {
        RequestEnvelope<MessageBody> envelope =
                GsonProvider.getGsonProvider().fromJson(
                        json,
                        new TypeToken<RequestEnvelope<MessageBody>>(){}.getType()
                );

        MessageBody body = envelope.getBody();
        onMessageResponse.accept(body);
    }
}
