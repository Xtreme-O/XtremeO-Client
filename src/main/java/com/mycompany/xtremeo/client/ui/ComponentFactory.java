package com.mycompany.xtremeo.client.ui;

import com.mycompany.xtremeo.client.service.audio.AudioService;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.StrokeLineCap;
import org.kordamp.ikonli.javafx.FontIcon;
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
    
    public static void configureAudioToggleButton(Button button, String iconStyleClass) {
        AudioService audioService = AudioService.getInstance();
        FontIcon soundIcon = new FontIcon();
        soundIcon.getStyleClass().add(iconStyleClass);
        
        button.setGraphic(soundIcon);
        button.setMnemonicParsing(false);
        
        updateAudioIcon(soundIcon, audioService.isBackgroundMusicEnabled());
        
        button.setOnAction(e -> {
            audioService.setBackgroundMusicEnabled(!audioService.isBackgroundMusicEnabled());
            updateAudioIcon(soundIcon, audioService.isBackgroundMusicEnabled());
        });
    }
    
    private static void updateAudioIcon(FontIcon icon, boolean enabled) {
        if (enabled) {
            icon.setIconLiteral("mdi2v-volume-high");
        } else {
            icon.setIconLiteral("mdi2v-volume-off");
        }
    }
    
}
