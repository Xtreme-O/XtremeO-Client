package com.mycompany.xtremeo.client.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.model.auth.request.LoginRequestBody;
import com.mycompany.xtremeo.client.model.auth.request.LogoutRequestBody;
import com.mycompany.xtremeo.client.model.auth.request.RegisterRequestBody;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.network.DispatcherMessageListener;
import com.mycompany.xtremeo.client.network.NetworkConfig;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.auth.LoginService;
import com.mycompany.xtremeo.client.service.auth.LogoutService;
import com.mycompany.xtremeo.client.service.auth.RegisterService;

import java.time.LocalDateTime;

public class ServicesIntegrationTest {

    private final RequestSender sender;

    public ServicesIntegrationTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ResponseDispatcher dispatcher = new ResponseDispatcher(gson);
        ClientConnection connection = new ClientConnection();
        connection.connect(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
        connection.startListening(
                new DispatcherMessageListener(dispatcher)
        );
        this.sender = new SocketRequestSender(gson, connection);
    }

    public RegisterService registerService() {
        return new RegisterService(sender);
    }

    public LoginService loginService() {
        return new LoginService(sender);
    }

    public LogoutService logoutService(){
        return new LogoutService(sender);
    }

    public void testRegister() {
        RegisterRequestBody body = new RegisterRequestBody(
                "elsobky",
                "1234",
                "src/main/resources/com/mycompany/xtremeo/client/images/avatars/CyberKing.png"
        );

        Header header = new Header("JSON", ActionType.REGISTER.name());

        RequestEnvelope<RegisterRequestBody> request =
                new RequestEnvelope<>(header, body);

        System.out.println("Sending REGISTER request...");
        registerService().send(request);
    }

    public void testLogin() {
        LoginRequestBody body = new LoginRequestBody(
                "elsobky",
                "1234"
        );

        Header header = new Header("JSON", ActionType.LOGIN.name());
        RequestEnvelope<LoginRequestBody> request =
                new RequestEnvelope<>(header, body);

        System.out.println("Sending LOGIN request...");
        loginService().send(request);
    }

    public void testLogout() {
        LogoutRequestBody body = new LogoutRequestBody(
                "elsobky"
        );

        Header header = new Header("JSON", ActionType.LOGOUT.name());
        RequestEnvelope<LogoutRequestBody> request =
                new RequestEnvelope<>(header, body);

        System.out.println("Sending LOGOUT request...");
        logoutService().send(request);
    }

}


class testo{
    public static void main(String[] args) throws InterruptedException {
        ServicesIntegrationTest test = new ServicesIntegrationTest();
        test.testRegister();
        Thread.sleep(3000);
        test.testLogin();
        Thread.sleep(3000);
        test.testLogout();
    }
}

