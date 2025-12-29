/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.app.Navigator;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Elsobky
 */

public class MainMenuController {

    @FXML private StackPane mainRoot;
    @FXML private Button btnCpu;
    @FXML private Button btnMultiplayer;

    @FXML
    public void initialize() {
        mainRoot.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), mainRoot);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @FXML
    void handlePlayCPU(ActionEvent event) {
        System.out.println("Starting Single Player...");
        // Navigate to game board
    }

    @FXML
    void handlePlayWithFreind(ActionEvent event) {
        System.out.println("Starting Play With Freind...");
        // Navigate to game board
    }
    
    
    @FXML
    void handleMultiplayer(ActionEvent event) {
        System.out.println("Starting Multiplayer...");
        // Navigate to Login/Register screen
    }
}
