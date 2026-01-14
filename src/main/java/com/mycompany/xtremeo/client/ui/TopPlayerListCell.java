package com.mycompany.xtremeo.client.ui;


import com.mycompany.xtremeo.client.model.common.PlayerProfile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


public class TopPlayerListCell extends ListCell<PlayerProfile> {
    
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
    protected void updateItem(PlayerProfile profile, boolean empty) {
        super.updateItem(profile, empty);

        if (empty || profile == null) {
            setGraphic(null);
        } else {
            int rank = getIndex() + 1;
            rankLabel.setText(String.valueOf(rank));
            rankLabel.getStyleClass().clear();

            rankLabel.getStyleClass().add("rank-number");
            switch (rank) {
                case 1 -> rankLabel.getStyleClass().add("rank-number-gold");
                case 2 -> rankLabel.getStyleClass().add("rank-number-silver");
                case 3 -> rankLabel.getStyleClass().add("rank-number-bronze");
                default -> rankLabel.getStyleClass().add("rank-number-default");
            }

            AvatarFactory.setup(avatarImage, profile.player().getAvatarUrl(), 24);

            nameLabel.setText(profile.player().getUsername());
            xpLabel.setText(String.valueOf(profile.elo()));

            setGraphic(row);
        }
    }
}

