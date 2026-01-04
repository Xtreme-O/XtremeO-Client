package com.mycompany.xtremeo.client.util;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class UIUtils {

    public static void setupPulseAnimation(Node node) {
        FadeTransition pulse = new FadeTransition(Duration.millis(1000), node);
        pulse.setFromValue(1.0);
        pulse.setToValue(0.5);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
    }

    public static HBox createLogCard(String message) {
        HBox card = new HBox();
        card.getStyleClass().add("log-item-card");
        card.setAlignment(Pos.CENTER_LEFT);
        Label msgLabel = new Label(message);
        msgLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12;");
        card.getChildren().add(msgLabel);
        return card;
    }
}