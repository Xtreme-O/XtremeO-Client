package com.mycompany.xtremeo.client.service.auth;

import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.model.auth.request.LoginRequestBody;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
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

    public void login(String username, String password) {
        LoginRequestBody body = new LoginRequestBody(username, password);
        Header header = new Header("JSON",ActionType.LOGIN.name());
        RequestEnvelope<LoginRequestBody> request =
                new RequestEnvelope<>(header, body);
        send(request);
    }
}
