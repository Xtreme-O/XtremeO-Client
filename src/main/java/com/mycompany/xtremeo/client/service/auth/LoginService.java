package com.mycompany.xtremeo.client.service.auth;

import com.google.gson.Gson;
import com.mycompany.xtremeo.client.model.auth.LoginRequestBody;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.RequestSender;

public class LoginService implements AuthService<LoginRequestBody>{
    private final RequestSender sender;

    public LoginService(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(RequestEnvelope<LoginRequestBody> request) {
        sender.send(request);
    }
}
