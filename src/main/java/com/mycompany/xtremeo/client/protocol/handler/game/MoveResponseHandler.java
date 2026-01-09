package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.listener.game.MoveUIListener;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import javafx.application.Platform;

public class MoveResponseHandler implements ResponseHandler<Move> {

    private final MoveUIListener uiListener;

    public MoveResponseHandler(MoveUIListener uiListener) {
        this.uiListener = uiListener;
    }

    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<Move> envelope =
                gson.fromJson(
                        json,
                        new TypeToken<RequestEnvelope<Move>>(){}.getType()
                );

        Move body = envelope.getBody();
        uiListener.onMoveReceived(body);
    }
}
