package com.mycompany.xtremeo.client.service.game;

import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.enums.GameState;
import com.mycompany.xtremeo.client.model.common.MessageBody;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.model.game.SessionMessageBody;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.RequestSender;

public class SessionMessageService {
    private final RequestSender sender;

    public SessionMessageService(RequestSender request) {
        this.sender = request;
    }

    public void sendMove(Move move, GameState state) {
        Header header = new Header("JSON", ActionType.SESSION_MESSAGE.name());
        SessionMessageBody body = new SessionMessageBody(move, state);
        RequestEnvelope<SessionMessageBody> request = new RequestEnvelope<>(header,body);
        sender.send(request);
    }

    public void sendInGameMessage(String msg){
        Header header = new Header("JSON", ActionType.IN_GAME_MESSAGE.name());
        MessageBody body = new MessageBody(msg);
        RequestEnvelope<MessageBody> request = new RequestEnvelope<>(header,body);
        sender.send(request);
    }
}
