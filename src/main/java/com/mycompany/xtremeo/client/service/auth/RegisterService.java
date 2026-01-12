package com.mycompany.xtremeo.client.service.auth;

import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.model.auth.request.RegisterRequestBody;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.RequestSender;
import com.mycompany.xtremeo.client.service.SocketRequestSender;

public class RegisterService implements AuthService<RegisterRequestBody> {

    private RegisterService() {
    }

    private static RegisterService registerService;

    public static RegisterService getInstance() {
        if (registerService == null)
            registerService = new RegisterService();
        return registerService;
    }

    @Override
    public void send(RequestEnvelope<RegisterRequestBody> request) {
        SocketRequestSender.getInstance().send(request);
    }

    public void register(String username, String password, String avatar) {
        RegisterRequestBody body = new RegisterRequestBody(username, password, avatar);
        Header header = new Header("JSON", ActionType.REGISTER.name());
        RequestEnvelope<RegisterRequestBody> request =
                new RequestEnvelope<>(header, body);
        send(request);
    }
}
