package com.mycompany.xtremeo.client.service.auth;

import com.mycompany.xtremeo.client.model.auth.request.RegisterRequestBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.RequestSender;

public class RegisterService implements AuthService<RegisterRequestBody>{
    private final RequestSender sender;

    public RegisterService(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(RequestEnvelope<RegisterRequestBody> request) {
        sender.send(request);
    }
}
