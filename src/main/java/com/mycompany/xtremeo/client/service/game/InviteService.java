package com.mycompany.xtremeo.client.service.game;

import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.model.game.InviteBody;
import com.mycompany.xtremeo.client.model.game.InviteConfirmationBody;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.service.SocketRequestSender;

public class InviteService {

    private InviteService() {
    }

    private static InviteService inviteService;

    public static InviteService getInstance() {
        if (inviteService == null)
            inviteService = new InviteService();
        return inviteService;
    }

    public void sendInvite(Player player1, Player player2) {
        Header header = new Header("JSON", ActionType.INVITE.name());
        InviteBody body = new InviteBody(player1, player2);
        RequestEnvelope<InviteBody> request = new RequestEnvelope<>(header, body);
        SocketRequestSender.getInstance().send(request);
    }

    public void confirmInvite(int senderId, int receiverID) {
        Header header = new Header("JSON", ActionType.INVITE_CONFIRMED.name());
        InviteConfirmationBody body = new InviteConfirmationBody(senderId, receiverID);
        RequestEnvelope<InviteConfirmationBody> request = new RequestEnvelope<>(header, body);
        SocketRequestSender.getInstance().send(request);
    }

    public void declinedInvite(int senderId, int receiverID) {
        Header header = new Header("JSON", ActionType.INVITE_DECLINED.name());
        InviteConfirmationBody body = new InviteConfirmationBody(senderId, receiverID);
        RequestEnvelope<InviteConfirmationBody> request = new RequestEnvelope<>(header, body);
        SocketRequestSender.getInstance().send(request);
    }

    public void boradcastInvite(int senderId) {
        Header header = new Header("JSON", ActionType.INVITE_ALL.name());
        InviteConfirmationBody body = new InviteConfirmationBody(senderId, 0);
        RequestEnvelope<InviteConfirmationBody> request = new RequestEnvelope<>(header, body);
        SocketRequestSender.getInstance().send(request);
    }
}
