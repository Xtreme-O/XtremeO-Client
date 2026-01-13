package com.mycompany.xtremeo.client.data;

import com.mycompany.xtremeo.client.enums.PlayerStatus;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import com.mycompany.xtremeo.client.model.common.Score;
import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class DataProvider {

    private static final String AVATAR_BASE_PATH =
            "/com/mycompany/xtremeo/client/images/avatars/";

    private static final Player currentPlayer = new Player(
            1,
            "NeonPlayerOne",
            avatarPath("NeonPlayerOne"),
            PlayerStatus.ONLINE,
            LocalDateTime.now().minusDays(30),
            LocalDateTime.now()
    );


    private static final PlayerProfile currentPlayerProfile =
            new PlayerProfile(
                    currentPlayer,
                    new Score(
                            1,
                            1,
                            "TIC_TAC_TOE",
                            24,
                            8,
                            6,
                            7
                    ),
                    2000
            );

    private DataProvider() {
    }

    // ==================== UTIL ====================

    private static String avatarPath(String name) {
        return AVATAR_BASE_PATH + name + ".png";
    }

    // ==================== CURRENT USER ====================

    public static PlayerProfile getCurrentUserProfile() {
        return currentPlayerProfile;
    }

    public static int getCurrentUserRank() {
        return 42;
    }

    // ==================== ONLINE PLAYERS ====================

    public static List<PlayerProfile> getOnlinePlayers() {
        List<PlayerProfile> players = new ArrayList<>();

        players.add(createProfile(2, "PixelMaster", 750, 45, 12, 5));
        players.add(createProfile(3, "SarahConnor", 1400, 102, 88, 12));
        players.add(createProfile(4, "X_O_Strategist", 2500, 310, 45, 18));
        players.add(createProfile(5, "NovicePlayer", 120, 2, 15, 2));
        players.add(createProfile(
                6, "BusyBee", 1100, 78, 34, 9, PlayerStatus.INGAME
        ));
        players.add(createProfile(7, "Glitch_01", 600, 22, 20, 4));

        return players;
    }

    // ==================== TOP PLAYERS ====================
    // same model, different sorting in UI

    public static List<PlayerProfile> getTopPlayers() {
        List<PlayerProfile> players = new ArrayList<>();

        players.add(createProfile(10, "CyberKing", 5400, 420, 90, 25));
        players.add(createProfile(11, "TicTacMaster", 5350, 398, 102, 22));
        players.add(createProfile(12, "GridLock", 4800, 355, 120, 19));

        return players;
    }

    private static PlayerProfile createProfile(
            int id,
            String username,
            int elo,
            int wins,
            int losses,
            int longestStreak
    ) {
        return createProfile(
                id,
                username,
                elo,
                wins,
                losses,
                longestStreak,
                PlayerStatus.ONLINE
        );
    }

    private static PlayerProfile createProfile(
            int id,
            String username,
            int elo,
            int wins,
            int losses,
            int longestStreak,
            PlayerStatus status
    ) {
        Player player = new Player(
                id,
                username,
                avatarPath(username),
                status,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now()
        );

        Score score = new Score(
                id,
                id,
                "TIC_TAC_TOE",
                wins,
                losses,
                0,
                longestStreak
        );

        return new PlayerProfile(player, score, elo);
    }

    public static int getOnlineCount() {
        return 124;
    }

    // ==================== CHAT ====================

    public static List<ChatMessageData> getChatMessages() {
        List<ChatMessageData> messages = new ArrayList<>();
        List<PlayerProfile> online = getOnlinePlayers();

        messages.add(new ChatMessageData(
                online.get(0).player(),
                "Anyone up for a quick match? 🎮",
                "10:42 AM"
        ));

        messages.add(new ChatMessageData(
                online.get(1).player(),
                "I'm waiting in the lobby!",
                "10:43 AM"
        ));

        messages.add(new ChatMessageData(
                currentPlayer,
                "Sent you an invite Sarah! Let's go!",
                "10:44 AM"
        ));

        messages.add(new ChatMessageData(
                online.get(5).player(),
                "GG @X_O_Strategist, that was close!",
                "10:45 AM"
        ));

        return messages;
    }

    // ==================== DEMO ====================

    public static Player getDemoChallenger() {
        List<PlayerProfile> players = getOnlinePlayers();
        return players
                .get(new Random().nextInt(players.size()))
                .player();
    }

    // ==================== TIME ====================

    public static String formatCurrentTime() {
        return LocalTime.now()
                .format(DateTimeFormatter.ofPattern("h:mm a"));
    }
}
