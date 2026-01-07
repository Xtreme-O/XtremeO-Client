package com.mycompany.xtremeo.client.service.auth;

import com.google.gson.Gson;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;

public class AuthServiceImpl implements AuthService {

    private final Gson gson = new Gson();
    private final ClientConnection connection;

    public AuthServiceImpl(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public <T> void send(RequestEnvelope<T> request) {
        String json = gson.toJson(request);
        System.out.println("Request: " + json);
        connection.send(json);
    }
}
