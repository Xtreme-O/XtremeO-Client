package com.mycompany.xtremeo.client.ui;

import javafx.animation.RotateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import org.kordamp.ikonli.javafx.FontIcon;

public class PlayButtonStateManager {
    
    private final HBox buttonContent;
    private final FontIcon icon;
    private final Label label;
    
    private Arc spinner;
    private RotateTransition spinnerAnimation;

    public PlayButtonStateManager(HBox buttonContent, FontIcon icon, Label label) {
        this.buttonContent = buttonContent;
        this.icon = icon;
        this.label = label;
    }

    public void setPlayNowState() {
        label.setText("PLAY NOW");
        stopSpinner();
        replaceSpinnerWithIcon();
    }


    public void setMatchmakingState() {
        label.setText("MATCHMAKING...");
        createAndStartSpinner();
        replaceIconWithSpinner();
    }

    private void createAndStartSpinner() {
        spinner = ComponentFactory.createSpinner(16, Color.BLACK);
        spinnerAnimation = ComponentFactory.createSpinAnimation(spinner);
        spinnerAnimation.play();
    }

    private void stopSpinner() {
        if (spinnerAnimation != null) {
            spinnerAnimation.stop();
        }
    }

    private void replaceIconWithSpinner() {
        int iconIndex = buttonContent.getChildren().indexOf(icon);
        if (iconIndex >= 0) {
            buttonContent.getChildren().set(iconIndex, spinner);
        } else {
            buttonContent.getChildren().add(0, spinner);
        }
    }

    private void replaceSpinnerWithIcon() {
        if (spinner != null) {
            int spinnerIndex = buttonContent.getChildren().indexOf(spinner);
            if (spinnerIndex >= 0) {
                buttonContent.getChildren().set(spinnerIndex, icon);
            } else {
                buttonContent.getChildren().add(0, icon);
            }
        }
    }
}

