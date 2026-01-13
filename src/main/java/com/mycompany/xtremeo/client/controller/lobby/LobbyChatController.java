package com.mycompany.xtremeo.client.controller.lobby;

import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;
import com.mycompany.xtremeo.client.protocol.handler.message.GlobalMessageHandler;
import com.mycompany.xtremeo.client.service.lobby.ChatService;
import com.mycompany.xtremeo.client.service.lobby.PlayerService;
import com.mycompany.xtremeo.client.ui.AvatarFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LobbyChatController {

    @FXML private ScrollPane chatScroll;
    @FXML private VBox messagesContainer;
    @FXML private HBox quickChatContainer;
    @FXML private TextField txtChatInput;
    @FXML private Button btnSend;

    private final ChatService chatService = ChatService.getInstance();

    @FXML
    public void initialize() {
        loadMessages();
        GlobalMessageHandler.setOnMessageResponse(chatService::receiveMessage);
        chatService.setMessageListener(this::addMessageToUI);
        messagesContainer.heightProperty().addListener((obs, old, val) -> {
            chatScroll.setVvalue(1.0);
        });

        setupQuickChat();
        txtChatInput.setOnAction(e -> handleSend());
    }

    private void loadMessages() {
        messagesContainer.getChildren().clear();
        for (ChatMessageData msg : chatService.getMessages()) {
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

    private void addMessageToUI(ChatMessageData msg) {
        Platform.runLater(() -> {
            var currentProfile = PlayerService.getInstance().getCurrentPlayer();
            HBox row = msg.sender().equals(currentProfile.player()) ?
                    createSelfMessage(msg) : createOtherMessage(msg);
            messagesContainer.getChildren().add(row);
        });
    }

    private HBox createOtherMessage(ChatMessageData msg) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.TOP_LEFT);

        ImageView avatar = AvatarFactory.create(msg.sender().getAvatarUrl(), 34);

        VBox content = new VBox(5);

        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);
        Label sender = new Label(msg.sender().getUsername());
        sender.getStyleClass().add("message-sender");
        Label time = new Label(msg.time());
        time.getStyleClass().add("message-time");
        header.getChildren().addAll(sender, time);

        VBox bubble = new VBox();
        bubble.getStyleClass().add("message-bubble");
        bubble.setMaxWidth(220);
        Label text = new Label(msg.message());
        text.getStyleClass().add("message-text");
        text.setWrapText(true);
        bubble.getChildren().add(text);

        content.getChildren().addAll(header, bubble);
        row.getChildren().addAll(avatar, content);
        return row;
    }

    private HBox createSelfMessage(ChatMessageData msg) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.TOP_RIGHT);

        VBox content = new VBox(5);
        content.setAlignment(Pos.TOP_RIGHT);

        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_RIGHT);
        Label time = new Label(msg.time());
        time.getStyleClass().add("message-time");
        header.getChildren().add(time);

        VBox bubble = new VBox();
        bubble.getStyleClass().add("message-bubble-self");
        bubble.setMaxWidth(220);
        Label text = new Label(msg.message());
        text.getStyleClass().add("message-text-self");
        text.setWrapText(true);
        bubble.getChildren().add(text);

        content.getChildren().addAll(header, bubble);
        row.getChildren().add(content);
        return row;
    }
}
