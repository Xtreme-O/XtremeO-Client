package com.mycompany.xtremeo.client.controller.lobby;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.service.lobby.MatchmakingService;
import com.mycompany.xtremeo.client.ui.LobbyComponentFactory;

public class LobbyBannerContainerController {

    @FXML
    private VBox bannerContainerRoot;

    private final MatchmakingService matchmakingService = MatchmakingService.getInstance();

    @FXML
    public void initialize() {
        System.out.println("Controller instance = " + this);
        matchmakingService.setOnChallengeReceived(this::addChallengeBanner);

        matchmakingService.setOnPendingChallengesChanged(players -> {
            Platform.runLater(() -> {
                bannerContainerRoot.getChildren().clear(); // remove all old banners
                for (Player player : players) {
                    addChallengeBanner(player);
                }
            });
        });

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(matchmakingService::simulateDemoChallenge);
                Platform.runLater(matchmakingService::simulateDemoChallenge);

            } catch (InterruptedException ignored) {
            }
        }).start();
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
            bannerContainerRoot.getChildren().add(banner);
        });
    }
}
