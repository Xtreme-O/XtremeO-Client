package com.mycompany.xtremeo.client.service;

import com.google.gson.Gson;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;

public class SocketRequestSender implements RequestSender{
    private final Gson gson;
    private final ClientConnection connection;

    public SocketRequestSender(Gson gson, ClientConnection connection) {
        this.gson = gson;
        this.connection = connection;
    }

    @Override
    public void send(RequestEnvelope<?> request) {
        String json = gson.toJson(request);
        connection.send(json);
    }
}
