package com.mycompany.xtremeo.client.ui;

import com.mycompany.xtremeo.client.model.lobby.TopPlayerData;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


public class TopPlayerListCell extends ListCell<TopPlayerData> {
    
    private final HBox row;
    private final Label rankLabel;
    private final ImageView avatarImage;
    private final Label nameLabel;
    private final Label xpLabel;

    public TopPlayerListCell() {
        row = new HBox();
        row.getStyleClass().add("top-player-row");
        row.setMinWidth(0);

        rankLabel = new Label();
        rankLabel.getStyleClass().add("rank-number");
        rankLabel.setMinWidth(18);

        avatarImage = new ImageView();
        avatarImage.setFitWidth(24);
        avatarImage.setFitHeight(24);
        avatarImage.setPreserveRatio(true);


        nameLabel = new Label();
        nameLabel.getStyleClass().add("top-player-name");
        nameLabel.setMinWidth(0);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        nameLabel.setAlignment(Pos.CENTER);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        xpLabel = new Label();
        xpLabel.getStyleClass().add("top-player-xp");
        xpLabel.setMinWidth(0);
        xpLabel.setAlignment(Pos.CENTER_RIGHT);


        row.getChildren().addAll(rankLabel, avatarImage, nameLabel, xpLabel);
        row.setAlignment(Pos.CENTER_LEFT);

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setStyle("-fx-background-color: transparent;");
        setPadding(new Insets(0, 2, 8, 2));

        listViewProperty().addListener(
                (obs, oldList, newList) -> {
            if (newList != null) {
                prefWidthProperty().bind(newList.widthProperty().subtract(2));
                maxWidthProperty().bind(newList.widthProperty().subtract(2));
                row.prefWidthProperty().bind(newList.widthProperty().subtract(2));
                row.maxWidthProperty().bind(newList.widthProperty().subtract(2));
            }
        });
    }

    @Override
    protected void updateItem(TopPlayerData player, boolean empty) {
        super.updateItem(player, empty);

        if (empty || player == null) {
            setGraphic(null);
        } else {
            int rank = player.rank();
            rankLabel.setText(String.valueOf(rank));
            
            rankLabel.getStyleClass().clear();
            rankLabel.getStyleClass().add("rank-number");
            
            if (rank == 2) {
                rankLabel.getStyleClass().add("rank-number-silver");
            } else if (rank == 3) {
                rankLabel.getStyleClass().add("rank-number-bronze");
            }

            AvatarFactory.setup(avatarImage, player.avatarUrl(), 24);

            nameLabel.setText(player.name());
            xpLabel.setText(String.valueOf(player.score()));

            setGraphic(row);
        }
    }
}

