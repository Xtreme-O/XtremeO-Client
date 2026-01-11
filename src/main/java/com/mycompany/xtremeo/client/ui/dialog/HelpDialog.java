package com.mycompany.xtremeo.client.ui.dialog;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HelpDialog {

    public static void show(StackPane parent) {
        if (parent == null) return;

        ModalDialog dialog = new ModalDialog(parent);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(400);
        content.setPrefWidth(400);

        StackPane icon = createHelpIcon();

        Label titleLabel = new Label("About XtremO");
        titleLabel.getStyleClass().add("help-title");

        Label versionLabel = new Label("Version 1.0");
        versionLabel.getStyleClass().add("help-version");

        Separator separator = new Separator();
        separator.getStyleClass().add("help-separator");

        VBox aboutSection = new VBox(12);
        aboutSection.setAlignment(Pos.CENTER_LEFT);
        aboutSection.setMaxWidth(350);

        Label aboutTitle = new Label("About Us");
        aboutTitle.getStyleClass().add("help-section-title");

        Text aboutText = new Text(
            "XtremO is an exciting Tic-Tac-Toe game that brings classic gameplay " +
            "to the modern era. Challenge yourself against AI opponents of varying " +
            "difficulty levels, play with friends locally, or compete in online " +
            "multiplayer matches. Experience smooth gameplay, track your game history, " +
            "and climb the leaderboard!"
        );
        aboutText.getStyleClass().add("help-text");
        aboutText.setTextAlignment(TextAlignment.JUSTIFY);
        aboutText.setWrappingWidth(350);

        aboutSection.getChildren().addAll(aboutTitle, aboutText);

        VBox featuresSection = new VBox(12);
        featuresSection.setAlignment(Pos.CENTER_LEFT);
        featuresSection.setMaxWidth(350);

        Label featuresTitle = new Label("Features");
        featuresTitle.getStyleClass().add("help-section-title");

        VBox featureList = new VBox(8);
        featureList.getChildren().addAll(
            createFeatureItem("• Play against AI with multiple difficulty levels"),
            createFeatureItem("• Local multiplayer with friends"),
            createFeatureItem("• Online multiplayer lobby"),
            createFeatureItem("• Game history tracking"),
            createFeatureItem("• Player statistics and rankings")
        );

        featuresSection.getChildren().addAll(featuresTitle, featureList);

        Button closeButton = new Button("Got It");
        closeButton.getStyleClass().add("help-button");
        closeButton.setOnAction(e -> dialog.close(null));

        content.getChildren().addAll(
            icon, titleLabel, versionLabel, separator,
            aboutSection, featuresSection, closeButton
        );

        dialog.addContent(content);
        dialog.show();
    }

    private static StackPane createHelpIcon() {
        StackPane iconContainer = new StackPane();
        iconContainer.setMaxSize(60, 60);
        iconContainer.setMinSize(60, 60);

        Circle bg = new Circle(30);
        bg.setFill(Color.web("#f4af25"));

        Circle innerCircle = new Circle(18);
        innerCircle.setFill(Color.TRANSPARENT);
        innerCircle.setStroke(Color.WHITE);
        innerCircle.setStrokeWidth(3);

        Circle dot = new Circle(6);
        dot.setFill(Color.WHITE);
        dot.setTranslateY(8);

        iconContainer.getChildren().addAll(bg, innerCircle, dot);
        return iconContainer;
    }

    private static Label createFeatureItem(String text) {
        Label item = new Label(text);
        item.getStyleClass().add("help-feature-item");
        item.setWrapText(true);
        return item;
    }
}

