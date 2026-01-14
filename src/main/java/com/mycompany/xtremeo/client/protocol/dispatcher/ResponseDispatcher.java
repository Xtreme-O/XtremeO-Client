package com.mycompany.xtremeo.client.protocol.dispatcher;

import com.mycompany.xtremeo.client.protocol.handler.*;
import com.mycompany.xtremeo.client.protocol.handler.auth.LoginResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.auth.LogoutResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.auth.RegisterResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.common.ErrorResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.game.*;
import com.mycompany.xtremeo.client.protocol.handler.lobby.LobbyResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.lobby.PlayerConnectedResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.lobby.PlayerDisconnectedResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.message.GlobalMessageHandler;
import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.message.InGameMessageHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.EnumMap;
import java.util.Map;

public class ResponseDispatcher {

    private final Map<ActionType, ResponseHandler<?>> handlers;

    public ResponseDispatcher() {
        this.handlers = new EnumMap<>(ActionType.class);
        registerHandlers();
    }

    private void registerHandlers() {
        handlers.put(ActionType.LOGIN, new LoginResponseHandler());
        handlers.put(ActionType.LOGOUT, new LogoutResponseHandler());
        handlers.put(ActionType.REGISTER, new RegisterResponseHandler());
        handlers.put(ActionType.SESSION_MESSAGE, new MoveResponseHandler());
        handlers.put(ActionType.GLOBAL_MESSAGE, new GlobalMessageHandler());
        handlers.put(ActionType.IN_GAME_MESSAGE, new InGameMessageHandler());
        handlers.put(ActionType.ERROR, new ErrorResponseHandler());
        handlers.put(ActionType.INVITE, new InviteResponseHandler());
        handlers.put(ActionType.INVITE_CONFIRMED, new InviteConfirmResponseHandler());
        handlers.put(ActionType.INVITE_DECLINED, new InviteDeclinedResponseHandler());
        handlers.put(ActionType.LOBBY, new LobbyResponseHandler());
        handlers.put(ActionType.SESSION_ENDED, new SessionEndHandler());
        handlers.put(ActionType.PARTNER_DISCONNECTED, new PartnerDisconnectedResponseHandler());
        handlers.put(ActionType.ACTIVE_PLAYER_CONNECTED, new PlayerConnectedResponseHandler());
        handlers.put(ActionType.ACTIVE_PLAYER_DISCONNECTED, new PlayerDisconnectedResponseHandler());

    }

    public void dispatch(String json) {
        try {
            RequestEnvelope<?> envelope =
                    GsonProvider.getGsonProvider().fromJson(json, RequestEnvelope.class);
            ActionType action =
                    ActionType.valueOf(envelope.getHeader().getAction());

            ResponseHandler<?> handler = handlers.get(action);
            handler.handle(json);
        } catch (Exception e) {
            System.err.println("Unknown action");
            e.printStackTrace();
        }
    }
}

