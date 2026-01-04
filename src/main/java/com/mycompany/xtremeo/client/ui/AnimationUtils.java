package com.mycompany.xtremeo.client.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationUtils {

    public static void fadeIn(Node node, double durationMs, Runnable onFinished) {
        FadeTransition fade = new FadeTransition(Duration.millis(durationMs), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        if (onFinished != null) fade.setOnFinished(e -> onFinished.run());
        fade.play();
    }

    public static void fadeOut(Node node, double durationMs, Runnable onFinished) {
        FadeTransition fade = new FadeTransition(Duration.millis(durationMs), node);
        fade.setFromValue(1);
        fade.setToValue(0);
        if (onFinished != null) fade.setOnFinished(e -> onFinished.run());
        fade.play();
    }

    public static void scaleIn(Node node, double durationMs, double fromScale) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(durationMs), node);
        scale.setFromX(fromScale);
        scale.setFromY(fromScale);
        scale.setToX(1);
        scale.setToY(1);
        scale.play();
    }

    public static void scaleOut(Node node, double durationMs, double toScale) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(durationMs), node);
        scale.setToX(toScale);
        scale.setToY(toScale);
        scale.play();
    }

    public static void popIn(Node overlay, Node content, double durationMs) {
        overlay.setOpacity(0);
        content.setScaleX(0.8);
        content.setScaleY(0.8);
        fadeIn(overlay, durationMs, null);
        scaleIn(content, durationMs * 1.25, 0.8);
    }

    public static void popOut(Node overlay, Node content, double durationMs, Runnable onFinished) {
        scaleOut(content, durationMs, 0.8);
        fadeOut(overlay, durationMs, onFinished);
    }
}

