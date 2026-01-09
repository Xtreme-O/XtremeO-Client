package com.mycompany.xtremeo.client.protocol.handler.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.model.common.MessageBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import javafx.application.Platform;

public class GlobalMessageHandler implements ResponseHandler<MessageBody> {

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<MessageBody> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<MessageBody>>(){}.getType()
                );

        MessageBody body = envelope.getBody();
        System.out.println("Created at: "+ body.getMessage());

    }
}
