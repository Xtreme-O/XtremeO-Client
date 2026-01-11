package com.mycompany.xtremeo.client.data;

import com.mycompany.xtremeo.client.enums.PlayerStatus;
import com.mycompany.xtremeo.client.enums.Tier;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;
import com.mycompany.xtremeo.client.model.lobby.TopPlayerData;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class DataProvider {

    private static final String AVATAR_BASE_PATH = "/com/mycompany/xtremeo/client/images/avatars/";

    private static final Player currentPlayer = new Player(
            1, "NeonPlayerOne",
            avatarPath("NeonPlayerOne"),
            PlayerStatus.ONLINE,
            LocalDateTime.now().minusDays(30),
            LocalDateTime.now()
    );

    private DataProvider() {
    }

    // ==================== UTILITY ====================

    private static String avatarPath(String name) {
        return AVATAR_BASE_PATH + name + ".png";
    }

    // ==================== CURRENT USER ====================

    public static Player getCurrentUser() {
        return currentPlayer;
    }

    public static int getCurrentUserScore() {
        return 2000;
    }

    public static Tier getCurrentUserTier() {
        return Tier.fromScore(getCurrentUserScore());
    }

    public static int getCurrentUserRank() {
        return 42;
    }

    public static int getCurrentUserWins() {
        return 24;
    }

    public static int getCurrentUserLosses() {
        return 8;
    }

    // ==================== ONLINE PLAYERS ====================

    public static List<LobbyPlayerData> getOnlinePlayers() {
        List<LobbyPlayerData> players = new ArrayList<>();

        players.add(new LobbyPlayerData(
                new Player(2, "PixelMaster", avatarPath("PixelMaster"),
                        PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now()),
                750, 45, 12
        ));

        players.add(new LobbyPlayerData(
                new Player(3, "SarahConnor", avatarPath("SarahConnor"),
                        PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now()),
                1400, 102, 88
        ));

        players.add(new LobbyPlayerData(
                new Player(4, "X_O_Strategist", avatarPath("X_O_Strategist"),
                        PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now()),
                2500, 310, 45
        ));

        players.add(new LobbyPlayerData(
                new Player(5, "NovicePlayer", avatarPath("NovicePlayer"),
                        PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now()),
                120, 2, 15
        ));

        players.add(new LobbyPlayerData(
                new Player(6, "BusyBee", avatarPath("BusyBee"),
                        PlayerStatus.INGAME, LocalDateTime.now(), LocalDateTime.now()),
                1100, 78, 34
        ));

        players.add(new LobbyPlayerData(
                new Player(7, "Glitch_01", avatarPath("Glitch_01"),
                        PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now()),
                600, 22, 20
        ));

        return players;
    }

    public static int getOnlineCount() {
        return 124;
    }

    // ==================== TOP PLAYERS ====================

    public static List<TopPlayerData> getTopPlayers() {
        List<TopPlayerData> players = new ArrayList<>();
        players.add(new TopPlayerData(1, "CyberKing", avatarPath("CyberKing"), 5400));
        players.add(new TopPlayerData(2, "TicTacMaster", avatarPath("TicTacMaster"), 5350));
        players.add(new TopPlayerData(3, "GridLock", avatarPath("GridLock"), 4800));
        return players;
    }

    // ==================== CHAT MESSAGES ====================

    public static List<ChatMessageData> getChatMessages() {
        Player pixelMaster = new Player(2, "PixelMaster", avatarPath("PixelMaster"),
                PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now());
        Player sarahConnor = new Player(3, "SarahConnor", avatarPath("SarahConnor"),
                PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now());
        Player glitch01 = new Player(7, "Glitch_01", avatarPath("Glitch_01"),
                PlayerStatus.ONLINE, LocalDateTime.now(), LocalDateTime.now());

        List<ChatMessageData> messages = new ArrayList<>();
        messages.add(new ChatMessageData(pixelMaster, "Anyone up for a quick match? 🎮", "10:42 AM"));
        messages.add(new ChatMessageData(sarahConnor, "I'm waiting in the lobby!", "10:43 AM"));
        messages.add(new ChatMessageData(currentPlayer, "Sent you an invite Sarah! Let's go!", "10:44 AM"));
        messages.add(new ChatMessageData(glitch01, "GG @X_O_Strategist, that was close!", "10:45 AM"));
        return messages;
    }

    // ==================== DEMO CHALLENGER ====================

    public static Player getDemoChallenger() {
        return getOnlinePlayers().get(new Random().nextInt(getOnlinePlayers().size())).player();
    }

    // ==================== UTILITY ====================

    public static String formatCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    // ==================== DATA RECORDS ====================

    public record LobbyPlayerData(Player player, int score, int wins, int losses) {
        public Tier tier() {
            return Tier.fromScore(score);
        }

        public boolean isInGame() {
            return player.getStatus() == PlayerStatus.INGAME;
        }
    }


}
