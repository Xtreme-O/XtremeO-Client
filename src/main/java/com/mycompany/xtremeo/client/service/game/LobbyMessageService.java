package com.mycompany.xtremeo.client.service.game;

import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.SocketRequestSender;

public class LobbyMessageService {
    private static LobbyMessageService lobbyMessageService;

    private LobbyMessageService(){}

    public static LobbyMessageService getInstance(){
        if (lobbyMessageService == null)
            lobbyMessageService = new LobbyMessageService();
        return lobbyMessageService;
    }

    public void send(ChatMessageData msg){
        Header header = new Header("JSON", ActionType.GLOBAL_MESSAGE.name());
        RequestEnvelope<ChatMessageData> request = new RequestEnvelope<>(header,msg);
        SocketRequestSender.getInstance().send(request);
    }
}
