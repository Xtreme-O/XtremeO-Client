package com.mycompany.xtremeo.client.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.controller.BoardController;
import com.mycompany.xtremeo.client.listener.auth.LoginUIListener;
import com.mycompany.xtremeo.client.listener.auth.RegisterUIListener;
import com.mycompany.xtremeo.client.listener.game.MoveUIListener;
import com.mycompany.xtremeo.client.listener.global.GlobalMessageUIListener;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;

import java.time.LocalDateTime;

public class ClientDispatcherTest {


    public static void main(String[] args) {
        MoveUIListener moveListener = new BoardController();
        // for test not implement
        //TODO: replace the objects with controller object that implement this listeners
        LoginUIListener loginListener = new LoginUIListener() {
            @Override
            public void onLoginResponse(Player player) {
                System.out.println("Login Successfully from UI: " + player.toString());
            }

            @Override
            public void onLoginError(String errorMessage) {
                LoginUIListener.super.onLoginError(errorMessage);
            }
        };
        RegisterUIListener registerListener = new RegisterUIListener() {

            @Override
            public void onRegisterResponse(Player player) {
                System.out.println("Register Successfully from UI: " + player.toString());
            }

            @Override
            public void onRegisterError(String errorMessage) {
                RegisterUIListener.super.onRegisterError(errorMessage);
            }
        };
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ResponseDispatcher dispatcher = new ResponseDispatcher(gson, moveListener,
                loginListener, registerListener);
        ClientConnection connection = new ClientConnection();
        connection.connect(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
        connection.startListening(
                new DispatcherMessageListener(dispatcher)
        );
        System.out.println("Client connected and listening...");
    }
}
