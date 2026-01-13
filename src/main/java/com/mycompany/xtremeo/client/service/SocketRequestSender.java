package com.mycompany.xtremeo.client.service;

import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.util.GsonProvider;

public class SocketRequestSender implements RequestSender {
    private SocketRequestSender() {
    }

    private static SocketRequestSender socket;

    public static SocketRequestSender getInstance() {
        if (socket == null)
            socket = new SocketRequestSender();
        return socket;
    }

    @Override
    public void send(RequestEnvelope<?> request) {
        String json = GsonProvider.getGsonProvider().toJson(request);
        ClientConnection.getInstance().send(json);
    }
}
