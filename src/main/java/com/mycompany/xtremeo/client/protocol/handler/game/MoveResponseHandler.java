package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.game.OnlineOpponent;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.util.GsonProvider;

public class MoveResponseHandler implements ResponseHandler<Move> {

    @Override
    public void handle(String json) {
        RequestEnvelope<Move> envelope =
                GsonProvider.getGsonProvider().fromJson(
                        json,
                        new TypeToken<RequestEnvelope<Move>>(){}.getType()
                );

        Move move = envelope.getBody();
        OnlineOpponent.getInstance().onMoveReceived(move);
    }
}
