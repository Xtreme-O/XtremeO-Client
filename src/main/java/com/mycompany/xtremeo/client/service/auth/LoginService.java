package com.mycompany.xtremeo.client.service.auth;

import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.model.auth.request.LoginRequestBody;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.SocketRequestSender;

public class LoginService implements AuthService<LoginRequestBody> {

    private LoginService() {
    }

    private static LoginService loginService;

    public static LoginService getInstance() {
        if (loginService == null)
            loginService = new LoginService();
        return loginService;
    }

    @Override
    public void send(RequestEnvelope<LoginRequestBody> request) {
        SocketRequestSender.getInstance().send(request);
    }

    public void login(String username, String password) {
        LoginRequestBody body = new LoginRequestBody(username, password);
        Header header = new Header("JSON", ActionType.LOGIN.name());
        RequestEnvelope<LoginRequestBody> request =
                new RequestEnvelope<>(header, body);
        send(request);
    }
}
