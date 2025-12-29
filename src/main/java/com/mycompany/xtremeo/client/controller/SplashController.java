package com.mycompany.xtremeo.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class SplashController {

    @FXML private ProgressBar progressBar;
    @FXML private Label progressLabel;
    @FXML private Label statusLabel;
    @FXML private StackPane rootPane;
    
    @FXML
    public void initialize() {
        
        new Thread(() -> {
            try {
                String[] stages = {"Initializing game engine...", "Loading assets...", "Ready!"};
                
                for (int i = 0; i <= 100; i++) {
                    final double progress = i / 100.0;
                    final int percent = i;
                    
                    Platform.runLater(() -> {
                        progressBar.setProgress(progress);
                        progressLabel.setText(percent + "%");
                        
                        if (percent < 50) statusLabel.setText(stages[0]);
                        else if (percent < 90) statusLabel.setText(stages[1]);
                        else statusLabel.setText(stages[2]);
                    });

                    Thread.sleep(30);
                }
                
                Thread.sleep(500);
                Platform.runLater(this::loadMainGame);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadMainGame() {
        System.out.println("Loading Main Game...");
        // Switch to Main screen
    }
}