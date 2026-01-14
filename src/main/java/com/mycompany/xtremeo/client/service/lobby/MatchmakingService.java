package com.mycompany.xtremeo.client.service.lobby;

import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.protocol.handler.game.InviteResponseHandler;
import com.mycompany.xtremeo.client.service.game.InviteService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MatchmakingService {

    private static MatchmakingService instance;
    private Consumer<List<Player>> onPendingChallengesChanged;
    private final List<Player> pendingChallenges = new ArrayList<>();
    private final InviteService inviteService = InviteService.getInstance();

    private MatchmakingService() {
        InviteResponseHandler.setOnInviteResponseConsumer(e -> {
            PlayerService service = PlayerService.getInstance();

            Player currentPlayer = service.getCurrentPlayer().player();
            Player player1 = e.player1();
            Player player2 = e.player2();

            if (currentPlayer.equals(player1)) {
                receiveChallenge(e.player2());
            }
            else if (currentPlayer.equals(player2)) {
                receiveChallenge(e.player1());
            }
        });
    }

    public static MatchmakingService getInstance() {
        if (instance == null) {
            instance = new MatchmakingService();
        }
        return instance;
    }



    public void setOnPendingChallengesChanged(Consumer<List<Player>> callback) {
        this.onPendingChallengesChanged = callback;
    }

    public void receiveChallenge(Player from) {
        if(!pendingChallenges.contains(from)){
            pendingChallenges.add(from);
        }

        if (onPendingChallengesChanged != null) {
            onPendingChallengesChanged.accept(pendingChallenges);
        }

    }

    public void acceptChallenge(Player player) {
        PlayerService service = PlayerService.getInstance();
        Player currentPlayer = service.getCurrentPlayer().player();
        inviteService.confirmInvite(currentPlayer.getId(), player.getId());
        pendingChallenges.remove(player);
        if (onPendingChallengesChanged != null) {
            onPendingChallengesChanged.accept(pendingChallenges);
        }

    }

    public void declineChallenge(Player player) {
        System.out.println("Challenge declined from: " + player.getUsername());
        pendingChallenges.remove(player);
        if (onPendingChallengesChanged != null) {
            onPendingChallengesChanged.accept(pendingChallenges);
        }
    }

    public void challengePlayer(Player player) {
        System.out.println("Challenging: " + player.getUsername());
        PlayerService service = PlayerService.getInstance();
        Player currentPlayer = service.getCurrentPlayer().player();
        inviteService.sendInvite(currentPlayer, player);
        
    }

    public void startMatchmaking() {
        System.out.println("Starting matchmaking...");
        inviteService.boradcastInvite(PlayerService.getInstance().getCurrentPlayer().player().getId());
    }

    public void clear() {
        instance = null;
        pendingChallenges.clear();
    }
}
