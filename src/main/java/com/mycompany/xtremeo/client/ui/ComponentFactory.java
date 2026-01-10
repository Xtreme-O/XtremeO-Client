package com.mycompany.xtremeo.client.ui;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

public final class ComponentFactory {

    private ComponentFactory() {}

    public static Arc createSpinner(double size, Color color) {
        double radius = size / 2;
        Arc spinner = new Arc(radius, radius, radius, radius, 0, 270);
        spinner.setFill(Color.TRANSPARENT);
        spinner.setStroke(color);
        spinner.setStrokeWidth(2.5);
        spinner.setStrokeLineCap(StrokeLineCap.ROUND);
        return spinner;
    }

    public static RotateTransition createSpinAnimation(Arc spinner) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.8), spinner);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        return rotate;
    }
}
