package com.mycompany.xtremeo.client.service.game;

import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.enums.GameState;
import com.mycompany.xtremeo.client.model.common.MessageBody;
import com.mycompany.xtremeo.client.model.game.SessionMessageBody;
import com.mycompany.xtremeo.client.model.game.SessionMove;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.SocketRequestSender;

public class SessionMessageService {

    private SessionMessageService() {
    }

    private static SessionMessageService sessionMessageService;

    public static SessionMessageService getInstance() {
        if (sessionMessageService == null)
            sessionMessageService = new SessionMessageService();
        return sessionMessageService;
    }

    public void sendMove(SessionMove move, GameState state) {
        Header header = new Header("JSON", ActionType.SESSION_MESSAGE.name());
        SessionMessageBody body = new SessionMessageBody(move, state);
        RequestEnvelope<SessionMessageBody> request = new RequestEnvelope<>(header, body);
        SocketRequestSender.getInstance().send(request);
    }

    public void sendInGameMessage(String msg) {
        Header header = new Header("JSON", ActionType.IN_GAME_MESSAGE.name());
        MessageBody body = new MessageBody(msg);
        RequestEnvelope<MessageBody> request = new RequestEnvelope<>(header, body);
        SocketRequestSender.getInstance().send(request);
    }

    public void sendEndSessionMessage() {
        Header header = new Header("JSON", ActionType.SESSION_ENDED.name());
        RequestEnvelope<Void> request = new RequestEnvelope<>(header, null);
        SocketRequestSender.getInstance().send(request);
    }
}
