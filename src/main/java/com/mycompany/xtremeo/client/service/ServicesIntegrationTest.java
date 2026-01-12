package com.mycompany.xtremeo.client.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.enums.GameState;
import com.mycompany.xtremeo.client.enums.PlayerStatus;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.network.DispatcherMessageListener;
import com.mycompany.xtremeo.client.network.NetworkConfig;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;
import com.mycompany.xtremeo.client.service.auth.LoginService;
import com.mycompany.xtremeo.client.service.auth.LogoutService;
import com.mycompany.xtremeo.client.service.auth.RegisterService;
import com.mycompany.xtremeo.client.service.game.InviteService;
import com.mycompany.xtremeo.client.service.game.SessionMessageService;

import java.time.LocalDateTime;

public class ServicesIntegrationTest {

    private final RequestSender sender;

    public ServicesIntegrationTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ResponseDispatcher dispatcher = new ResponseDispatcher(gson);
        ClientConnection connection = ClientConnection.getInstance();
        connection.connect(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
        connection.startListening(
                new DispatcherMessageListener(dispatcher)
        );
        this.sender = new SocketRequestSender(gson, connection);
    }

    // Auth Services
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

    // Invite Services
    public InviteService inviteService() {
        return new InviteService(sender);
    }

    public void testSendInvite() {
        Player player = new Player(1, "sobky", "null", PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now());
        inviteService().sendInvite(player, player);
    }

    public void testConfirmInvite() {
        inviteService().confirmInvite(1, 2);
    }

    public void testDeclinedInvite() {
        inviteService().declinedInvite(1, 2);
    }

    // sessionService
    public SessionMessageService sessionMessageService() {
        return new SessionMessageService(sender);
    }

    public void testSendSessionMessage() {
        sessionMessageService().sendMove(
                new Move(new InGamePlayer("sobky", "O"),
                        1, 2),
                GameState.IN_PROGRESS
        );
    }

    public void testSendInGameMessage(){
        sessionMessageService().sendInGameMessage("Hello from tester!!");
    }
}


class testo {
    public static void main(String[] args) throws InterruptedException {
        ServicesIntegrationTest test = new ServicesIntegrationTest();
        // auth
        test.testRegister();
        Thread.sleep(500);
        test.testLogin();
        Thread.sleep(500);
        test.testLogout();
        // Invitation
        Thread.sleep(500);
        test.testSendInvite();
        Thread.sleep(500);
        test.testConfirmInvite();
        Thread.sleep(500);
        test.testDeclinedInvite();
        // SessionMessage
        Thread.sleep(500);
        test.testSendSessionMessage();
        Thread.sleep(500);
        test.testSendInGameMessage();
    }
}

