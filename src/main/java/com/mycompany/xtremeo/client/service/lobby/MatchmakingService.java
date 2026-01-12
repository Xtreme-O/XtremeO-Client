package com.mycompany.xtremeo.client.service.lobby;

import com.mycompany.xtremeo.client.data.DataProvider;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.protocol.handler.game.InviteResponseHandler;
import com.mycompany.xtremeo.client.service.SocketRequestSender;
import com.mycompany.xtremeo.client.service.game.InviteService;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MatchmakingService {

    private static MatchmakingService instance;
    private Consumer<Player> onChallengeReceived;
    private Consumer<List<Player>> onPendingChallengesChanged;
    private final List<Player> pendingChallenges = new ArrayList<>();
    private Runnable onMatchFound;

    private MatchmakingService() {
        InviteResponseHandler.setOnInviteResponseConsumer(e -> {
            PlayerService service = PlayerService.getInstance();

            Player currentPlayer = service.getCurrentPlayer();
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

    public void setOnChallengeReceived(Consumer<Player> callback) {
        this.onChallengeReceived = callback;
    }

    public void setOnMatchFound(Runnable callback) {
        this.onMatchFound = callback;
    }

    public void setOnPendingChallengesChanged(Consumer<List<Player>> callback) {
        this.onPendingChallengesChanged = callback;
    }

    public void receiveChallenge(Player from) {
        pendingChallenges.add(from);

        if (onChallengeReceived != null) {
            onChallengeReceived.accept(from);
        }

    }

    public void simulateDemoChallenge() {
        receiveChallenge(DataProvider.getDemoChallenger());
    }

    public void acceptChallenge(Player player) {
        PlayerService service = PlayerService.getInstance();
        Player currentPlayer = service.getCurrentPlayer();
        InviteService inviteService = new InviteService(new SocketRequestSender(GsonProvider.getGsonProvider(), ClientConnection.getInstance()));
        inviteService.confirmInvite(currentPlayer.getId(), player.getId());
        System.out.println("Challenge accepted from: " + player.getUsername());
        
        
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
        // TODO: Send challenge via Socket
        PlayerService service = PlayerService.getInstance();

        Player currentPlayer = service.getCurrentPlayer();
        InviteService inviteService = new InviteService(new SocketRequestSender(GsonProvider.getGsonProvider(), ClientConnection.getInstance()));
        inviteService.sendInvite(currentPlayer, player);

    }

    public void startMatchmaking() {
        System.out.println("Starting matchmaking...");
        // TODO: Connect to matchmaking service

        if (onMatchFound != null) {
            // TODO: Call the callback to notify the controller that a match has been found
            onMatchFound.run();
        }
    }

    public void cancelMatchmaking() {
        System.out.println("Matchmaking cancelled");
    }

    public void clear() {
        onChallengeReceived = null;
        onPendingChallengesChanged = null;
        onMatchFound = null;
        pendingChallenges.clear();
    }
}
