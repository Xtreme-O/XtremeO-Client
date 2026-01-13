package com.mycompany.xtremeo.client.service.lobby;

import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.model.lobby.LobbyBody;
import com.mycompany.xtremeo.client.network.NetworkConfig;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.SocketRequestSender;
import com.mycompany.xtremeo.client.service.auth.LogoutService;

import java.util.function.Consumer;

public class LobbyService {

    private static LobbyService instance;

    private final PlayerService playerService;
    private final ChatService chatService;
    private final MatchmakingService matchmakingService;

    private Consumer<Boolean> onLogout;

    private LobbyService() {
        playerService = PlayerService.getInstance();
        chatService = ChatService.getInstance();
        matchmakingService = MatchmakingService.getInstance();
    }

    public static LobbyService getInstance() {
        if (instance == null) {
            instance = new LobbyService();
        }
        return instance;
    }

    public PlayerService players() {
        return playerService;
    }

    public ChatService chat() {
        return chatService;
    }

    public MatchmakingService matchmaking() {
        return matchmakingService;
    }

    public void sendLoadLobbyRequest() {
        Header header = new Header(NetworkConfig.PROTOCOL, ActionType.LOBBY.name());
        RequestEnvelope<LobbyBody> request = new RequestEnvelope<>(header, null);
        SocketRequestSender.getInstance().send(request);
    }

    public void setOnLogout(Consumer<Boolean> callback) {
        this.onLogout = callback;
    }

    public void logout() {
        System.out.println("Logging out...");
        matchmakingService.clear();
        boolean success = true;
        LogoutService.getInstance().logout(playerService.getCurrentPlayer().getUsername());
        if (onLogout != null) {
            onLogout.accept(success);
        }
    }


    public void showHistory() {
        System.out.println("Showing history...");
    }

}
