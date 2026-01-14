package com.mycompany.xtremeo.client.controller.lobby;

import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.service.lobby.MatchmakingService;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
import com.mycompany.xtremeo.client.ui.LobbyComponentFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LobbyPlayerListController {

    @FXML private Label lblOnlineCount;
    @FXML private TextField txtSearch;
    @FXML private GridPane playersGrid;

    private final PlayerService playerService = PlayerService.getInstance();
    
    @FXML
    public void initialize() {
        lblOnlineCount.textProperty().bind(
            playerService.onlineCountProperty().asString().concat(" Players Online")
        );

        playerService.getOnlinePlayers().addListener((javafx.collections.ListChangeListener<PlayerProfile>) change -> {
            Platform.runLater(() -> refreshPlayerList(txtSearch.getText()));
        });
        txtSearch.textProperty().addListener((obs, old, text) -> refreshPlayerList(text));
        
        Platform.runLater(() -> refreshPlayerList(""));
    }


    private void refreshPlayerList(String searchText) {
        playersGrid.getChildren().clear();

        var players = playerService.filterPlayers(searchText);
        int col = 0;
        int row = 0;

        for (PlayerProfile lobbyPlayer : players) {
            VBox card = LobbyComponentFactory.createPlayerCard(lobbyPlayer, this::handleChallenge);
            playersGrid.add(card, col, row);

            col++;
            if (col >= 2) {
                col = 0;
                row++;
            }
        }
    }

    private void handleChallenge(Player player) {
        MatchmakingService.getInstance().challengePlayer(player);
    }
}
