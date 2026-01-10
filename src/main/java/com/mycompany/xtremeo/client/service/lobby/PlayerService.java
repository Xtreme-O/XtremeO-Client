package com.mycompany.xtremeo.client.service.lobby;

import com.mycompany.xtremeo.client.data.DataProvider;
import com.mycompany.xtremeo.client.data.DataProvider.LobbyPlayerData;
import com.mycompany.xtremeo.client.enums.Tier;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.model.lobby.TopPlayerData;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayerService {

    private static PlayerService instance;

    private Player currentPlayer;
    private Tier currentTier;
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final IntegerProperty rank = new SimpleIntegerProperty();
    private final IntegerProperty wins = new SimpleIntegerProperty();
    private final IntegerProperty losses = new SimpleIntegerProperty();

    private final IntegerProperty onlineCount = new SimpleIntegerProperty();
    private final ObservableList<LobbyPlayerData> onlinePlayers = FXCollections.observableArrayList();

    private final ObservableList<TopPlayerData> topPlayers = FXCollections.observableArrayList();

    private PlayerService() {
        loadData();
    }

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    private void loadData() {
        currentPlayer = DataProvider.getCurrentUser();
        score.set(DataProvider.getCurrentUserScore());
        currentTier = DataProvider.getCurrentUserTier();
        rank.set(DataProvider.getCurrentUserRank());
        wins.set(DataProvider.getCurrentUserWins());
        losses.set(DataProvider.getCurrentUserLosses());
        onlineCount.set(DataProvider.getOnlineCount());
        onlinePlayers.setAll(DataProvider.getOnlinePlayers());
        topPlayers.setAll(DataProvider.getTopPlayers());
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public String getAvatarUrl() {
        return currentPlayer != null ? currentPlayer.getAvatarUrl() : null;
    }

    public String getUsername() {
        return currentPlayer != null ? currentPlayer.getUsername() : "Guest";
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

    public ObservableList<LobbyPlayerData> getOnlinePlayers() {
        return onlinePlayers;
    }

    public ObservableList<LobbyPlayerData> filterPlayers(String search) {
        if (search == null || search.trim().isEmpty()) {
            return onlinePlayers;
        }
        String s = search.toLowerCase().trim();
        return onlinePlayers.filtered(p -> p.player().getUsername().toLowerCase().contains(s));
    }

    public ObservableList<TopPlayerData> getTopPlayers() {
        return topPlayers;
    }

    public void clear() {
        currentPlayer = null;
    }

    public void reload() {
        loadData();
    }
}
