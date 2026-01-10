package com.mycompany.xtremeo.client.controller.lobby;

import com.mycompany.xtremeo.client.model.lobby.TopPlayerData;
import com.mycompany.xtremeo.client.service.lobby.MatchmakingService;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
import com.mycompany.xtremeo.client.ui.AvatarFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class LobbyProfileController {

    @FXML private StackPane avatarContainer;
    @FXML private Label lblUsername;
    @FXML private Label lblTier;
    @FXML private Label lblWins;
    @FXML private Label lblLosses;
    @FXML private Label lblWinRate;

    @FXML private ImageView imgTop1;
    @FXML private Label lblTop1Name;
    @FXML private Label lblTop1Xp;
    @FXML private ImageView imgTop2;
    @FXML private Label lblTop2Name;
    @FXML private Label lblTop2Xp;
    @FXML private ImageView imgTop3;
    @FXML private Label lblTop3Name;
    @FXML private Label lblTop3Xp;

    private final PlayerService playerService = PlayerService.getInstance();
    private final MatchmakingService matchmakingService = MatchmakingService.getInstance();

    @FXML
    public void initialize() {
        bindProfile();
        bindTopPlayers();
    }

    private void bindProfile() {
        loadAvatar();
        lblUsername.setText(playerService.getUsername());

        playerService.rankProperty().addListener((obs, o, n) -> updateTierLabel());
        updateTierLabel();

        lblWins.textProperty().bind(playerService.winsProperty().asString());
        lblLosses.textProperty().bind(playerService.lossesProperty().asString());

        playerService.winsProperty().addListener((obs, o, n) -> updateWinRate());
        playerService.lossesProperty().addListener((obs, o, n) -> updateWinRate());
        updateWinRate();
    }

    private void loadAvatar() {
        String avatarUrl = playerService.getAvatarUrl();
        if (avatarUrl != null && !avatarUrl.isEmpty() && avatarContainer != null) {
            ImageView avatar = AvatarFactory.create(avatarUrl, 68);
            avatarContainer.getChildren().add(0, avatar);
        }
    }

    private void updateTierLabel() {
        lblTier.setText(playerService.getTier().toString() + " • #" + playerService.rankProperty().get());
    }

    private void updateWinRate() {
        lblWinRate.setText(playerService.getWinRate() + "%");
    }

    private void bindTopPlayers() {
        var topPlayers = playerService.getTopPlayers();
        if (topPlayers.size() >= 3) {
            setupTopPlayer(imgTop1, lblTop1Name, lblTop1Xp, topPlayers.get(0));
            setupTopPlayer(imgTop2, lblTop2Name, lblTop2Xp, topPlayers.get(1));
            setupTopPlayer(imgTop3, lblTop3Name, lblTop3Xp, topPlayers.get(2));
        }
    }

    private void setupTopPlayer(ImageView img, Label name, Label xp, TopPlayerData player) {
        AvatarFactory.setup(img, player.avatarUrl(), 24);
        name.setText(player.name());
        xp.setText(String.valueOf(player.score()));
    }

    @FXML
    private void handlePlayNow() {
        matchmakingService.startMatchmaking();
    }
}
