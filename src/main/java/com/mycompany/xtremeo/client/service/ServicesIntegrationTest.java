package com.mycompany.xtremeo.client.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.network.DispatcherMessageListener;
import com.mycompany.xtremeo.client.network.NetworkConfig;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;
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

    public LogoutService logoutService() {
        return new LogoutService(sender);
    }

    public void testRegister() {
        System.out.println("Sending REGISTER request");
        registerService().register("elsobky", "1234", "path/avatar.png");
    }

    public void testLogin() {
        System.out.println("Sending LOGIN request");
        loginService().login("elsobky", "1234");
    }

    public void testLogout() {
        System.out.println("Sending LOGOUT request...");
        logoutService().logout("elsobky");
    }

}


class testo {
    public static void main(String[] args) throws InterruptedException {
        ServicesIntegrationTest test = new ServicesIntegrationTest();
        test.testRegister();
        Thread.sleep(3000);
        test.testLogin();
        Thread.sleep(3000);
        test.testLogout();
    }
}

