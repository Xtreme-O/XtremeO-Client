package com.mycompany.xtremeo.client.protocol.dispatcher;

import com.google.gson.Gson;
import com.mycompany.xtremeo.client.game.OnlineOpponent;
import com.mycompany.xtremeo.client.listener.auth.LoginUIListener;
import com.mycompany.xtremeo.client.listener.auth.RegisterUIListener;
import com.mycompany.xtremeo.client.protocol.handler.*;
import com.mycompany.xtremeo.client.protocol.handler.auth.LoginResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.auth.RegisterResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.game.MoveResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.message.GlobalMessageHandler;
import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;

import java.util.EnumMap;
import java.util.Map;

public class ResponseDispatcher {

    private final Gson gson;
    private final Map<ActionType, ResponseHandler<?>> handlers;
    // TODO create all listeners
    LoginUIListener loginListener;
    RegisterUIListener registerListener;
//    GlobalMessageUIListener globalMessageListener;
    public ResponseDispatcher(Gson gson,
                              LoginUIListener loginListener,
                              RegisterUIListener registerListener) {
        this.gson = gson;
        this.handlers = new EnumMap<>(ActionType.class);
        this.loginListener = loginListener;
        this.registerListener = registerListener;
        registerHandlers();
    }

    private void registerHandlers() {
        handlers.put(ActionType.LOGIN, new LoginResponseHandler(loginListener));
        handlers.put(ActionType.REGISTER, new RegisterResponseHandler(registerListener));
        handlers.put(ActionType.MOVE, new MoveResponseHandler());
        handlers.put(ActionType.GLOBAL_MESSAGE, new GlobalMessageHandler());
    }

    public void dispatch(String json) {
        try {
            RequestEnvelope<?> envelope =
                    gson.fromJson(json, RequestEnvelope.class);
            ActionType action =
                    ActionType.valueOf(envelope.getHeader().getAction());

            ResponseHandler<?> handler = handlers.get(action);
            handler.handle(json, gson);
        } catch (Exception e) {
            System.err.println("Unknown action");
            e.printStackTrace();
        }
    }
}

