package com.mycompany.xtremeo.client.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;

import java.time.LocalDateTime;

public class ClientDispatcherTest {

    public static void main(String[] args) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ResponseDispatcher dispatcher = new ResponseDispatcher(gson);
        ClientConnection connection = new ClientConnection();
        connection.connect(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
        connection.startListening(
                new DispatcherMessageListener(dispatcher)
        );
        System.out.println("Client connected and listening...");
    }
}
