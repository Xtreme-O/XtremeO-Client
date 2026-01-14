package com.mycompany.xtremeo.client.service.lobby;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.data.DataProvider;
import com.mycompany.xtremeo.client.enums.Tier;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.model.lobby.LobbyBody;
import com.mycompany.xtremeo.client.protocol.handler.game.PartnerDisconnectedResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.lobby.PlayerConnectedResponseHandler;
import com.mycompany.xtremeo.client.protocol.handler.lobby.PlayerDisconnectedResponseHandler;
import com.mycompany.xtremeo.client.ui.dialog.ErrorDialog;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PlayerService {

    private static PlayerService instance;

    private PlayerProfile currentPlayer;
    private Tier currentTier;
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final IntegerProperty rank = new SimpleIntegerProperty();
    private final IntegerProperty wins = new SimpleIntegerProperty();
    private final IntegerProperty losses = new SimpleIntegerProperty();

    private final IntegerProperty onlineCount = new SimpleIntegerProperty();
    private final ObservableList<PlayerProfile> onlinePlayers = FXCollections.observableArrayList();

    private final ObservableList<PlayerProfile> topPlayers = FXCollections.observableArrayList();

    private PlayerService() {
        onlineCount.bind(Bindings.size(onlinePlayers));
        PlayerConnectedResponseHandler.setOnPlayerConnected(player -> {
            Platform.runLater(() -> {
                onlinePlayers.add(player);
            });
        });

        PlayerDisconnectedResponseHandler.setOnPlayerDisconnected(player -> {
            Platform.runLater(() -> {
                onlinePlayers.remove(player);
            });
        });

        PartnerDisconnectedResponseHandler.setOnPartnerDisconnected(player -> {
            Platform.runLater(() -> {
                Navigator.setRoot(Screen.LOBBY.getName());
                ErrorDialog.showServerError("An Error Occurred to your game partner");
            });
        });
    }

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public void loadData(LobbyBody body) {
        body.activeUsers().stream()
                .filter(p -> p.player().getId() == currentPlayer.player().getId())
                .findFirst()
                .ifPresent(this::setCurrentPlayer);

        var activeList = body.activeUsers()
                .stream()
                .filter(profile ->
                        profile.player().getId() != currentPlayer.player().getId())
                .toList();

        onlinePlayers.setAll(activeList);

        topPlayers.setAll(body.playersScores());

        int index = topPlayers.indexOf(currentPlayer);
        rank.set(index >= 0 ? index + 1 : topPlayers.size() + 1);
    }

    public PlayerProfile getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerProfile player) {
        this.currentPlayer = player;
        score.set(currentPlayer.elo());
        currentTier = currentPlayer.tier();
        wins.set(currentPlayer.score().wins());
        losses.set(currentPlayer.score().losses());
    }

    public String getAvatarUrl() {
        return currentPlayer != null ?
                currentPlayer.player().getAvatarUrl() : null;
    }

    public String getUsername() {
        return currentPlayer != null ?
                currentPlayer.player().getUsername() : "Guest";
    }

    public Tier getTier() {
        return currentTier;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public IntegerProperty rankProperty() {
        return rank;
    }

    public IntegerProperty winsProperty() {
        return wins;
    }

    public IntegerProperty lossesProperty() {
        return losses;
    }

    public int getWinRate() {
        int total = wins.get() + losses.get();
        return total > 0 ? (int) ((double) wins.get() / total * 100) : 0;
    }

    public IntegerProperty onlineCountProperty() {
        return onlineCount;
    }

    public ObservableList<PlayerProfile> getOnlinePlayers() {
        return onlinePlayers;
    }

    public ObservableList<PlayerProfile> filterPlayers(String search) {
        if (search == null || search.trim().isEmpty()) {
            return onlinePlayers;
        }
        String s = search.toLowerCase().trim();
        return onlinePlayers.filtered(p -> p.player().getUsername().toLowerCase().contains(s));
    }

    public ObservableList<PlayerProfile> getTopPlayers() {
        return topPlayers;
    }

    public void clear() {
        instance = null;
        currentPlayer = null;
    }

}
