package com.mycompany.xtremeo.client.controller.lobby;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.controller.BoardController;
import com.mycompany.xtremeo.client.game.OnlineOpponent;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.model.game.GameSession;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.model.game.InviteConfirmationResponseBody;
import com.mycompany.xtremeo.client.protocol.handler.game.InviteConfirmResponseHandler;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.service.lobby.MatchmakingService;
import com.mycompany.xtremeo.client.ui.LobbyComponentFactory;

import java.util.Optional;

public class LobbyBannerContainerController {

    @FXML
    private VBox bannerContainerRoot;

    private final MatchmakingService matchmakingService = MatchmakingService.getInstance();

    @FXML
    public void initialize() {
        System.out.println("Controller instance = " + this);
        matchmakingService.setOnPendingChallengesChanged(players -> {
            Platform.runLater(() -> {
                bannerContainerRoot.getChildren().clear();
                for (Player player : players) {
                    addChallengeBanner(player);
                }
            });
        });

        InviteConfirmResponseHandler.setOnInviteConfirmResponse(body -> {
            Platform.runLater(() -> {
                try {
                    GameSession session = createGameSessionFromInvite(body);
                    if (session != null) {
                        navigateToBoard(session);
                    }
                } catch (Exception e) {
                    System.err.println("ERROR: Failed to process invite confirmation: " + e.getMessage());
                }
            });
        });

    }

    private void addChallengeBanner(Player challenger) {
        Platform.runLater(() -> {
            var banner = LobbyComponentFactory.createChallengeBanner(
                    challenger,
                    () -> {
                        matchmakingService.acceptChallenge(challenger);
                    },
                    () -> {
                        matchmakingService.declineChallenge(challenger);
                    });
            
            StackPane bannerContainer = createBannerContainerWithCountdown(banner, challenger);
            bannerContainerRoot.getChildren().add(bannerContainer);
        });
    }

    private StackPane createBannerContainerWithCountdown(HBox banner, Player challenger) {
        Label countdownLabel = new Label("10");
        countdownLabel.getStyleClass().add("challenge-countdown");
        
        StackPane bannerContainer = new StackPane();
        bannerContainer.getChildren().add(banner);
        StackPane.setAlignment(countdownLabel, Pos.TOP_RIGHT);
        StackPane.setMargin(countdownLabel, new Insets(8, 12, 0, 0));
        bannerContainer.getChildren().add(countdownLabel);
        
        setupCountdownTimer(countdownLabel, bannerContainer, challenger);
        
        return bannerContainer;
    }

    private void setupCountdownTimer(Label countdownLabel, StackPane bannerContainer, Player challenger) {
        final int[] timeRemaining = {10};
        Timeline countdownTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining[0]--;
            if (timeRemaining[0] > 0) {
                countdownLabel.setText(String.valueOf(timeRemaining[0]));
            } else {
                bannerContainerRoot.getChildren().remove(bannerContainer);
                matchmakingService.declineChallenge(challenger);
            }
        }));
        countdownTimer.setCycleCount(10);
        countdownTimer.play();
    }

    private GameSession createGameSessionFromInvite(com.mycompany.xtremeo.client.model.game.InviteConfirmationResponseBody body) {
        var playerService = PlayerService.getInstance();
        PlayerProfile current = playerService.getCurrentPlayer();

        if (current == null) {
            System.err.println("ERROR: Current player is null!");
            return null;
        }

        PlayerProfile p1Profile = findPlayerProfile(body.player1().name(), current, playerService);
        PlayerProfile p2Profile = findPlayerProfile(body.player2().name(), current, playerService);

        if (p1Profile == null || p2Profile == null) {
            return null;
        }

        PlayerAssignment assignment = determinePlayerAssignment(current, body, p1Profile, p2Profile);

        InGamePlayer localPlayer = new InGamePlayer(assignment.localProfile().player(), assignment.localSymbol());
        InGamePlayer opponentPlayer = new InGamePlayer(assignment.opponentProfile().player(), assignment.opponentSymbol());

        OnlineOpponent onlineOpponent = OnlineOpponent.getInstance();
        onlineOpponent.setOpponentPlayer(opponentPlayer);

        return new GameSession(localPlayer, opponentPlayer, onlineOpponent);
    }

    private PlayerProfile findPlayerProfile(String username, PlayerProfile current, PlayerService playerService) {
        if (current.player().getUsername().equals(username)) {
            return current;
        }

        Optional<PlayerProfile> profile = playerService.getOnlinePlayers().stream()
                .filter(p -> p.player().getUsername().equals(username))
                .findFirst();

        if (profile.isEmpty()) {
            System.err.println("ERROR: Could not find player (" + username + ") in online list!");
            return null;
        }

        return profile.get();
    }

    private PlayerAssignment determinePlayerAssignment(PlayerProfile current,
                                                       InviteConfirmationResponseBody body,
                                                       PlayerProfile p1Profile,
                                                       PlayerProfile p2Profile) {
        String p1Symbol = body.player1().symbol();
        String p2Symbol = body.player2().symbol();

        if (p1Symbol == null || p1Symbol.isEmpty()) {
            p1Symbol = "X";
            System.err.println("WARNING: Player1 symbol was null, defaulting to X");
        }
        if (p2Symbol == null || p2Symbol.isEmpty()) {
            p2Symbol = "O";
            System.err.println("WARNING: Player2 symbol was null, defaulting to O");
        }

        if (current.player().getUsername().equals(body.player1().name())) {
            return new PlayerAssignment(
                    p1Profile,
                    p2Profile,
                    p1Symbol,
                    p2Symbol
            );
        } else {
            return new PlayerAssignment(
                    p2Profile,
                    p1Profile,
                    p2Symbol,
                    p1Symbol
            );
        }
    }

    private void navigateToBoard(GameSession session) {
        BoardController controller = Navigator.setRoot(Screen.BOARD.getName());
        if (controller != null) {
            controller.init(GameMode.ONLINE_PLAYER, true, session);
        } else {
            System.err.println("ERROR: BoardController is null after navigation!");
        }
    }

    private record PlayerAssignment(
            PlayerProfile localProfile,
            PlayerProfile opponentProfile,
            String localSymbol, String opponentSymbol) {}
}
