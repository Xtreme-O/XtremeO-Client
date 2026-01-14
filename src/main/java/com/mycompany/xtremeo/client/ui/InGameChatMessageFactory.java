/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.ui;
import com.mycompany.xtremeo.client.model.game.InGameChatMessage;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author wahid
 */


public final class InGameChatMessageFactory {

    private InGameChatMessageFactory() {}

    public static HBox createMessageRow(InGameChatMessage msg) {
        if (msg.sender() == null) {
            return createSystemMessage(msg);
        } else if (msg.isLocalPlayer()) {
            return createSelfMessage(msg);
        } else {
            return createOtherMessage(msg);
        }
    }

    public static HBox createOtherMessage(InGameChatMessage msg) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.TOP_LEFT);

        StackPane avatar = createPlayerAvatar(msg.sender());

        VBox content = new VBox(8);

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        Label sender = new Label(msg.sender().name());
        sender.getStyleClass().add("message-sender");
        sender.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        Label time = new Label(msg.time());
        time.getStyleClass().add("message-time");
        time.setStyle("-fx-font-size: 11;");
        header.getChildren().addAll(sender, time);

        VBox bubble = new VBox();
        bubble.getStyleClass().add("message-bubble");
        bubble.setMaxWidth(300);
        bubble.setStyle("-fx-padding: 12 16; -fx-background-radius: 4 14 14 14;");
        Label text = new Label(msg.message());
        text.getStyleClass().add("message-text");
        text.setStyle("-fx-font-size: 13;");
        text.setWrapText(true);
        bubble.getChildren().add(text);

        content.getChildren().addAll(header, bubble);
        row.getChildren().addAll(avatar, content);
        return row;
    }

    public static HBox createSelfMessage(InGameChatMessage msg) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.TOP_RIGHT);

        VBox content = new VBox(8);
        content.setAlignment(Pos.TOP_RIGHT);

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_RIGHT);
        Label time = new Label(msg.time());
        time.getStyleClass().add("message-time");
        time.setStyle("-fx-font-size: 11;");
        header.getChildren().add(time);

        VBox bubble = new VBox();
        bubble.getStyleClass().add("message-bubble-self");
        bubble.setMaxWidth(300);
        bubble.setStyle("-fx-padding: 12 16; -fx-background-radius: 14 4 14 14;");
        Label text = new Label(msg.message());
        text.getStyleClass().add("message-text-self");
        text.setStyle("-fx-font-size: 13;");
        text.setWrapText(true);
        bubble.getChildren().add(text);

        content.getChildren().addAll(header, bubble);
        row.getChildren().add(content);
        return row;
    }

    public static HBox createSystemMessage(InGameChatMessage msg) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER);

        Label text = new Label(msg.message());
        text.getStyleClass().add("message-time");
        text.setStyle("-fx-font-style: italic; -fx-font-size: 12;");

        row.getChildren().add(text);
        return row;
    }

    public static StackPane createPlayerAvatar(InGamePlayer player) {
        StackPane avatar = new StackPane();
        avatar.setMinSize(44, 44);
        avatar.setMaxSize(44, 44);
        avatar.setAlignment(Pos.CENTER);
        avatar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.08); -fx-background-radius: 50;");

        String displayText;
        
        if (player.name().equalsIgnoreCase("CPU")) {
            displayText = "🤖";
        } else if (player.symbol().equals("O")) {
            displayText = "👤";
        } else {
            displayText = player.symbol();
        }

        Label symbolLabel = new Label(displayText);
        symbolLabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.75); -fx-font-size: 20; -fx-font-weight: bold;");
        symbolLabel.setAlignment(Pos.CENTER);

        avatar.getChildren().add(symbolLabel);
        return avatar;
    }
}