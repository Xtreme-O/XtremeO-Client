package com.mycompany.xtremeo.client.ui;

import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;

public final class LobbyComponentFactory {

    private LobbyComponentFactory() {}

    public static VBox createPlayerCard(PlayerProfile lobbyPlayer, Consumer<Player> onChallenge) {
        VBox card = new VBox(12);
        card.getStyleClass().add("player-card");
        if (lobbyPlayer.isInGame()) {
            card.getStyleClass().add("player-card-ingame");
        }

        card.getChildren().addAll(
            createPlayerCardTop(lobbyPlayer),
            createPlayerCardButton(lobbyPlayer, onChallenge)
        );
        return card;
    }

    private static HBox createPlayerCardTop(PlayerProfile lobbyPlayer) {
        Player player = lobbyPlayer.player();

        ImageView avatar = AvatarFactory.create(player.getAvatarUrl(), 42);

        Label nameLabel = new Label(player.getUsername());
        nameLabel.getStyleClass().add("player-name");
        Label tierLabel = new Label(lobbyPlayer.tier().toString());
        tierLabel.getStyleClass().add("player-tier");
        VBox nameBox = new VBox(3, nameLabel, tierLabel);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(nameBox, Priority.ALWAYS);

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        if (lobbyPlayer.isInGame()) {
            Label badgeLabel = new Label("IN GAME");
            badgeLabel.getStyleClass().add("ingame-text");
            HBox badge = new HBox(badgeLabel);
            badge.getStyleClass().add("ingame-badge");
            badge.setAlignment(Pos.CENTER);
            topRow.getChildren().addAll(avatar, nameBox, badge);
        } else {
            Label winsLabel = new Label("W " + lobbyPlayer.score().wins());
            winsLabel.getStyleClass().add("player-wins");
            Label lossesLabel = new Label("L " + lobbyPlayer.score().losses());
            lossesLabel.getStyleClass().add("player-losses");
            VBox statsBox = new VBox(3, winsLabel, lossesLabel);
            statsBox.setAlignment(Pos.CENTER_RIGHT);
            topRow.getChildren().addAll(avatar, nameBox, statsBox);
        }

        return topRow;
    }

    private static Button createPlayerCardButton(PlayerProfile lobbyPlayer, Consumer<Player> onChallenge) {
        Button btn = new Button();
        btn.setMaxWidth(Double.MAX_VALUE);

        if (lobbyPlayer.isInGame()) {
            btn.setText("Spectate");
            btn.getStyleClass().add("spectate-btn");
            btn.setDisable(true);
        } else {
            btn.setText("Challenge");
            btn.getStyleClass().add("challenge-btn");
            if (onChallenge != null) {
                btn.setOnAction(e -> onChallenge.accept(lobbyPlayer.player()));
            }
        }

        return btn;
    }

    public static HBox createChallengeBanner(Player challenger, Runnable onAccept, Runnable onDecline) {
        FontIcon icon = new FontIcon("mdi2s-sword-cross");
        icon.getStyleClass().add("challenge-icon");
        StackPane iconContainer = new StackPane(icon);
        iconContainer.getStyleClass().add("challenge-icon-container");

        StackPane avatarContainer = new StackPane();
        avatarContainer.getStyleClass().add("challenger-avatar");
        if (challenger.getAvatarUrl() != null && !challenger.getAvatarUrl().isEmpty()) {
            avatarContainer.getChildren().add(AvatarFactory.create(challenger.getAvatarUrl(), 40));
        }

        Label title = new Label("Challenge Incoming!");
        title.getStyleClass().add("challenge-title");
        Label subtitle = new Label("From " + challenger.getUsername());
        subtitle.getStyleClass().add("challenge-from");
        VBox textBox = new VBox(2, title, subtitle);

        HBox left = new HBox(12, iconContainer, avatarContainer, textBox);
        left.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(left, Priority.ALWAYS);

        Button acceptBtn = new Button("ACCEPT");
        acceptBtn.getStyleClass().add("accept-btn");
        acceptBtn.setOnAction(e -> onAccept.run());

        Button declineBtn = new Button("DECLINE");
        declineBtn.getStyleClass().add("decline-btn");
        declineBtn.setOnAction(e -> onDecline.run());

        HBox banner = new HBox(14, left, new HBox(10, acceptBtn, declineBtn));
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.getStyleClass().add("challenge-banner");

        return banner;
    }
}

