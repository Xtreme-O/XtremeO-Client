package com.mycompany.xtremeo.client.service.lobby;

import com.mycompany.xtremeo.client.data.DataProvider;
import com.mycompany.xtremeo.client.model.common.Player;

import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;
import com.mycompany.xtremeo.client.service.game.LobbyMessageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.function.Consumer;

public class ChatService {

    private static ChatService instance;

    private final Player currentPlayer;
    private final ObservableList<ChatMessageData> messages = FXCollections.observableArrayList();
    private Consumer<ChatMessageData> messageListener;

    private ChatService() {
        currentPlayer = DataProvider.getCurrentUser();
        loadData();
    }

    public static ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }

    private void loadData() {
        messages.setAll(DataProvider.getChatMessages());
    }

    public ObservableList<ChatMessageData> getMessages() {
        return messages;
    }

    public void sendMessage(String message) {
        if (message == null || message.trim().isEmpty()) return;

        String time = DataProvider.formatCurrentTime();
        ChatMessageData msg = new ChatMessageData(currentPlayer, message.trim(), time);
        messages.add(msg);

        // TODO: MUST BE SENT AND THE LISTENER WILL BE CALLED AFTER IT BROADCASTED
        // TODO: DELETE THE FOLLOWING LINE OR HANDLE NOT TO BE ADDED TWICE
        messageListener.accept(msg);
        LobbyMessageService.getInstance().send(msg);
        System.out.println("Chat: " + message);
    }

    public void receiveMessage(ChatMessageData message) {
        messages.add(message);
        if (messageListener != null) {
            messageListener.accept(message);
        }
    }

    public void setMessageListener(Consumer<ChatMessageData> callback) {
        this.messageListener = callback;
    }

    public void clear() {
        messages.clear();
    }

    public void reload() {
        loadData();
    }
}
