package com.mycompany.xtremeo.client.controller.lobby;

import com.mycompany.xtremeo.client.data.DataProvider.LobbyPlayerData;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
import com.mycompany.xtremeo.client.ui.LobbyComponentFactory;
import com.mycompany.xtremeo.client.ui.dialog.ErrorDialog;
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

        txtSearch.textProperty().addListener((obs, old, text) -> refreshPlayerList(text));
        
        Platform.runLater(() -> refreshPlayerList(""));
    }

    private void refreshPlayerList(String searchText) {
        playersGrid.getChildren().clear();

        var players = playerService.filterPlayers(searchText);
        int col = 0;
        int row = 0;

        for (LobbyPlayerData lobbyPlayer : players) {
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
        ErrorDialog.show( 
            "Connection Error", 
            "Unable to challenge " + player.getUsername() + ". Server is not responding. Please try again later.");
    }
}
