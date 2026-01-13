package com.mycompany.xtremeo.client.controller.lobby;

import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.service.lobby.MatchmakingService;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
import com.mycompany.xtremeo.client.ui.AvatarFactory;
import com.mycompany.xtremeo.client.ui.PlayButtonStateManager;
import com.mycompany.xtremeo.client.ui.TopPlayerListCell;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;

public class LobbyProfileController {

    @FXML private StackPane avatarContainer;
    @FXML private Label lblUsername;
    @FXML private Label lblTier;
    @FXML private Label lblWins;
    @FXML private Label lblLosses;
    @FXML private Label lblWinRate;

    @FXML private ListView<PlayerProfile> listTopPlayers;
    @FXML private Button btnPlayNow;
    @FXML private HBox btnPlayNowContent;
    @FXML private FontIcon iconPlayNow;
    @FXML private Label lblPlayNow;

    private final PlayerService playerService = PlayerService.getInstance();
    private final MatchmakingService matchmakingService = MatchmakingService.getInstance();
    
    private PlayButtonStateManager buttonStateManager;
    private boolean isMatchmaking = false;


    @FXML
    public void initialize() {
        buttonStateManager = new PlayButtonStateManager(btnPlayNowContent, iconPlayNow, lblPlayNow);
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
        if (avatarContainer != null) {
            String avatarUrl = playerService.getAvatarUrl();
            ImageView avatar = AvatarFactory.create(avatarUrl, 68);
            avatarContainer.getChildren().addFirst(avatar);
        }
    }

    private void updateTierLabel() {
        lblTier.setText(playerService.getTier().toString() + " • #" + playerService.rankProperty().get());
    }

    private void updateWinRate() {
        lblWinRate.setText(playerService.getWinRate() + "%");
    }

    private void bindTopPlayers() {
        listTopPlayers.setCellFactory(lv -> new TopPlayerListCell());
        listTopPlayers.setItems(playerService.getTopPlayers());
        listTopPlayers.setFocusTraversable(false);
        listTopPlayers.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
        listTopPlayers.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    private void handlePlayNow() {
        if(isMatchmaking) {
            cancelMatchmaking();
        } else {
            startMatchmaking();
        }
    }

    private void cancelMatchmaking() {
        matchmakingService.cancelMatchmaking();
        buttonStateManager.setPlayNowState();
        isMatchmaking = false;
    }

    private void startMatchmaking() {
        buttonStateManager.setMatchmakingState();
        matchmakingService.startMatchmaking();
        isMatchmaking = true;
    }
}
