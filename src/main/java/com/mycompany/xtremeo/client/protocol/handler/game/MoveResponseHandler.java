package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.model.game.MoveBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import javafx.application.Platform;

public class MoveResponseHandler implements ResponseHandler<MoveBody> {
    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<MoveBody> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<MoveBody>>(){}.getType()
                );

        MoveBody body = envelope.getBody();
        System.out.println("MOVE COL: " + body.getCol());
        Platform.runLater(() -> {
            // update ui
        });
    }
}
