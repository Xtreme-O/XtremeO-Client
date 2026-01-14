package com.mycompany.xtremeo.client.service.recording;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.model.game.*;
import com.mycompany.xtremeo.client.model.recording.TicTacToeGameRecorder;

import java.time.LocalDateTime;

public class GameRecorderService {

    private final TicTacToeGameRecorder recorder;
    private final GameFileService gameFileService;

    private InGamePlayer playerX;
    private InGamePlayer playerO;
    private GameMode gameMode;
    private String playerUsername;

    public GameRecorderService() {
        this.recorder = new TicTacToeGameRecorder();
        this.gameFileService = new GameFileService(new JsonFileHandler());
    }

    public void startRecording(InGamePlayer playerX, InGamePlayer playerO, GameMode gameMode) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.gameMode = gameMode;
        recorder.reset();
    }

    public void setPlayerUsername(String username) {
        this.playerUsername = username;
    }

    public void recordMove(Move move) {
        recorder.recordMove(move);
    }

    public void saveGame(GameResult result, InGamePlayer winner, Difficulty difficulty) {
        if (gameFileService == null)
            return;

        GameHistoryEntry entry = new GameHistoryEntry(
                result, playerX, playerO, winner,
                LocalDateTime.now(), recorder.getEntries(), difficulty, gameMode);
        
        if (gameMode == GameMode.ONLINE_PLAYER && playerUsername != null) {
            gameFileService.saveGame(entry, playerUsername);
        } else {
            gameFileService.saveGame(entry);
        }
    }

}
