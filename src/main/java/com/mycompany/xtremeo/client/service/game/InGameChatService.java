package com.mycompany.xtremeo.client.service.game;

import com.mycompany.xtremeo.client.model.common.MessageBody;
import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.model.game.InGameChatMessage;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.protocol.handler.message.InGameMessageHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class InGameChatService {

    private static InGameChatService instance;

    private final ObservableList<InGameChatMessage> messages = FXCollections.observableArrayList();
    private Consumer<InGameChatMessage> messageListener;

    private InGamePlayer localPlayer;
    private InGamePlayer opponent;
    private GameMode gameMode;

    private InGameChatService() {
        InGameMessageHandler.setOnMessageResponse(this::receiveMessage);
    }

    public static synchronized InGameChatService getInstance() {
        if (instance == null) {
            instance = new InGameChatService();
        }
        return instance;
    }

    public void init(GameMode mode, InGamePlayer localPlayer, InGamePlayer opponent) {
        this.gameMode = mode;
        this.localPlayer = localPlayer;
        this.opponent = opponent;
        clear();
    }

    public void sendMessage(String message) {
        if (message == null || message.trim().isEmpty()) return;
        if (gameMode != GameMode.ONLINE_PLAYER) return;

        String time = formatCurrentTime();
        InGameChatMessage msg = new InGameChatMessage(localPlayer, message.trim(), time, true);
        messages.add(msg);

        if (messageListener != null) {
            messageListener.accept(msg);
        }

        SessionMessageService.getInstance().sendInGameMessage(message.trim());
    }

    private void receiveMessage(MessageBody body) {
        String time = formatCurrentTime();
        InGameChatMessage msg = new InGameChatMessage(opponent, body.getMessage(), time, false);
        messages.add(msg);

        if (messageListener != null) {
            messageListener.accept(msg);
        }
    }

    public void addLogEntry(InGamePlayer player, String logMessage) {
        if (gameMode == GameMode.ONLINE_PLAYER) return;

        String time = formatCurrentTime();
        boolean isLocal = player.equals(localPlayer);
        InGameChatMessage msg = new InGameChatMessage(player, logMessage, time, isLocal);
        messages.add(msg);

        if (messageListener != null) {
            messageListener.accept(msg);
        }
    }

    public void addSystemMessage(String message) {
        String time = formatCurrentTime();
        InGameChatMessage msg = new InGameChatMessage(null, message, time, false);
        messages.add(msg);

        if (messageListener != null) {
            messageListener.accept(msg);
        }
    }

    public ObservableList<InGameChatMessage> getMessages() {
        return messages;
    }

    public void setMessageListener(Consumer<InGameChatMessage> callback) {
        this.messageListener = callback;
    }

    public void clear() {
        messages.clear();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public InGamePlayer getLocalPlayer() {
        return localPlayer;
    }

    public InGamePlayer getOpponent() {
        return opponent;
    }

    private String formatCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a"));
    }
}
