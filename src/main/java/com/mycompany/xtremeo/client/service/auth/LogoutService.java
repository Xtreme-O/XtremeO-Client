package com.mycompany.xtremeo.client.service.auth;

import com.mycompany.xtremeo.client.model.auth.request.LoginRequestBody;
import com.mycompany.xtremeo.client.model.auth.request.LogoutRequestBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.RequestSender;

public class LogoutService implements AuthService<LogoutRequestBody>{
    private final RequestSender sender;

    public LogoutService(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(RequestEnvelope<LogoutRequestBody> request) {
        sender.send(request);
    }
}
