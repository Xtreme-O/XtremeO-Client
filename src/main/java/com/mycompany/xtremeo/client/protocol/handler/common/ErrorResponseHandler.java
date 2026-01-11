package com.mycompany.xtremeo.client.protocol.handler.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.common.ErrorBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;

import java.util.function.Consumer;

public class ErrorResponseHandler implements ResponseHandler<ErrorBody> {

    private static Consumer<ErrorBody> onErrorResponse;
    public static void setOnErrorResponse(Consumer<ErrorBody> consumer){
        onErrorResponse = consumer;
    }
    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<ErrorBody> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<ErrorBody>>(){}.getType()
                );

        ErrorBody error = envelope.getBody();
        System.err.println("Error from server: " + error.code() + " -> " + error.message());
        onErrorResponse.accept(error);
    }
}
