package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.game.OnlineOpponent;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.model.game.SessionMessageBody;
import com.mycompany.xtremeo.client.model.game.SessionMove;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.util.GsonProvider;

public class MoveResponseHandler implements ResponseHandler<SessionMessageBody> {

    @Override
    public void handle(String json) {
        RequestEnvelope<SessionMessageBody> envelope =
                GsonProvider.getGsonProvider().fromJson(
                        json,
                        new TypeToken<RequestEnvelope<SessionMessageBody>>(){}.getType()
                );

        SessionMessageBody messageBody = envelope.getBody();
        SessionMove sessionMove = messageBody.move();
        
        OnlineOpponent onlineOpponent = OnlineOpponent.getInstance();
        InGamePlayer opponentPlayer = onlineOpponent.getOpponentPlayer();
        if (opponentPlayer == null) {
            System.err.println("WARNING: opponentPlayer is null, creating from SessionMove");
            opponentPlayer = new InGamePlayer(
                    Player.fromUsername(sessionMove.player().name()),
                    sessionMove.player().symbol()
            );
        }
        
        Move move = new Move(opponentPlayer, sessionMove.row(), sessionMove.col());
        
        onlineOpponent.onMoveReceived(move);
    }
}
