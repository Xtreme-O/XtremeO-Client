package com.mycompany.xtremeo.client.controller.lobby;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.service.LobbyService;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LobbyHeaderController {

    @FXML private Button btnHistory;
    @FXML private Button btnLogout;

    private final LobbyService lobbyService = LobbyService.getInstance();
    private Runnable onHistoryClick;

    @FXML
    public void initialize() {
        lobbyService.setOnLogout(success -> {
            if (success) {
                Navigator.setRoot(Screen.MAIN.getName());
            }
        });
    }

    public void setOnHistoryClick(Runnable callback) {
        this.onHistoryClick = callback;
    }

    @FXML
    private void handleHistory() {
        if (onHistoryClick != null) {
            onHistoryClick.run();
        }
    }

    @FXML
    private void handleLogout() {
        lobbyService.logout();
    }
}
