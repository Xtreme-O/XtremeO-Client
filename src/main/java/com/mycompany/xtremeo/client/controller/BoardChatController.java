package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.model.game.InGameChatMessage;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import com.mycompany.xtremeo.client.service.game.InGameChatService;
import com.mycompany.xtremeo.client.ui.InGameChatMessageFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 *
 * @author wahid
 */
public class BoardChatController {

    @FXML private VBox chatPanelRoot;
    @FXML private Label chatTitle;
    @FXML private HBox liveIndicator;
    @FXML private ScrollPane chatScroll;
    @FXML private VBox messagesContainer;
    @FXML private HBox quickChatContainer;
    @FXML private VBox inputContainer;
    @FXML private TextField txtChatInput;
    @FXML private Button btnSend;

    private final InGameChatService chatService = InGameChatService.getInstance();
    private GameMode gameMode;

    @FXML
    public void initialize() {
        messagesContainer.heightProperty().addListener((obs, old, val) -> chatScroll.setVvalue(1.0));
        setupQuickChat();
        txtChatInput.setOnAction(e -> handleSend());
    }

    public void init(GameMode mode, InGamePlayer localPlayer, InGamePlayer opponent) {
        this.gameMode = mode;
        chatService.init(mode, localPlayer, opponent);
        chatService.setMessageListener(this::addMessageToUI);
        configureForMode();
        loadMessages();
    }

    private void configureForMode() {
        boolean isOnline = gameMode == GameMode.ONLINE_PLAYER;

        chatTitle.setText(isOnline ? "Game Chat" : "Game Log");

        liveIndicator.setVisible(isOnline);
        liveIndicator.setManaged(isOnline);

        quickChatContainer.setVisible(isOnline);
        quickChatContainer.setManaged(isOnline);

        inputContainer.setVisible(isOnline);
        inputContainer.setManaged(isOnline);
    }

    private void loadMessages() {
        messagesContainer.getChildren().clear();
        for (InGameChatMessage msg : chatService.getMessages()) {
            addMessageToUI(msg);
        }
    }

    private void setupQuickChat() {
        quickChatContainer.getChildren().forEach(node -> {
            if (node instanceof Button btn) {
                btn.setOnAction(e -> chatService.sendMessage(btn.getText()));
            }
        });
    }

    @FXML
    private void handleSend() {
        String text = txtChatInput.getText();
        if (text == null || text.trim().isEmpty()) return;
        chatService.sendMessage(text.trim());
        txtChatInput.clear();
    }

    private void addMessageToUI(InGameChatMessage msg) {
        Platform.runLater(() -> {
            HBox row = InGameChatMessageFactory.createMessageRow(msg);
            messagesContainer.getChildren().add(row);
        });
    }

    public void clear() {
        messagesContainer.getChildren().clear();
        chatService.clear();
    }

    public void addLogEntry(InGamePlayer player, String logMessage) {
        chatService.addLogEntry(player, logMessage);
    }

    public void addSystemMessage(String message) {
        chatService.addSystemMessage(message);
    }
}