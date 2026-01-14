package com.mycompany.xtremeo.client.service.recording;

import com.mycompany.xtremeo.client.model.game.GameHistoryEntry;
import com.mycompany.xtremeo.client.model.game.GameMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameFileService {

    private static final String DEFAULT_FOLDER = "recorded_games";

    private final JsonFileHandler fileHandler;
    private final File folder;

    public GameFileService(JsonFileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.folder = new File(DEFAULT_FOLDER);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create folder: " + folder.getAbsolutePath());
            }
        }
    }

    public void saveGame(GameHistoryEntry entry) {
        saveGame(entry, null);
    }

    public void saveGame(GameHistoryEntry entry, String username) {
        File targetFolder = folder;
        
        if (username != null && !username.isEmpty() && entry.gameMode() == GameMode.ONLINE_PLAYER) {
            targetFolder = new File(folder, username);
            if (!targetFolder.exists()) {
                boolean created = targetFolder.mkdirs();
                if (!created) {
                    throw new RuntimeException("Failed to create player folder: " + targetFolder.getAbsolutePath());
                }
            }
        }
        
        String filename = generateFilename(entry);
        File file = new File(targetFolder, filename);

        try {
            fileHandler.save(entry, file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save game", e);
        }
    }

    public GameHistoryEntry loadGame(String filename) {
        File file = new File(folder, filename);
        if (!file.exists())
            throw new RuntimeException("Game file not found: " + filename);

        try {
            return fileHandler.load(file, GameHistoryEntry.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load game", e);
        }
    }

    public List<GameHistoryEntry> loadGames() {
        List<GameHistoryEntry> games = new ArrayList<>();

        if (!folder.exists() || !folder.isDirectory()) {
            return games;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return games;

        for (File file : files) {
            try {
                games.add(loadGame(file.getName()));
            } catch (RuntimeException e) {
                System.err.println("Failed to load game from file: " + file.getName() + " - " + e.getMessage());
            }
        }

        return games.stream()
                .sorted(Comparator.comparing(GameHistoryEntry::time).reversed())
                .toList();
    }

    public List<GameHistoryEntry> loadOnlineGames(String username) {
        List<GameHistoryEntry> games = new ArrayList<>();

        if (username == null || username.isEmpty()) {
            return games;
        }

        File playerFolder = new File(folder, username);
        if (!playerFolder.exists() || !playerFolder.isDirectory()) {
            return games;
        }

        File[] files = playerFolder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return games;

        for (File file : files) {
            try {
                GameHistoryEntry entry = fileHandler.load(file, GameHistoryEntry.class);
                if (entry.gameMode() == GameMode.ONLINE_PLAYER) {
                    games.add(entry);
                }
            } catch (IOException e) {
                System.err.println("Failed to load game from file: " + file.getName() + " - " + e.getMessage());
            } catch (RuntimeException e) {
                System.err.println("Failed to load game from file: " + file.getName() + " - " + e.getMessage());
            }
        }

        return games.stream()
                .sorted(Comparator.comparing(GameHistoryEntry::time).reversed())
                .toList();
    }

    private String generateFilename(GameHistoryEntry entry) {
        String sanitizedTime = entry.time().toString().replace(":", "-");
        return "game_" + sanitizedTime + ".json";
    }
}
