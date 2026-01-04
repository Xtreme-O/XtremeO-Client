/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.model.GameMode;
import com.mycompany.xtremeo.client.ui.dialog.DifficultyDialog;
import com.mycompany.xtremeo.client.util.Screen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Elsobky
 */

public class MainMenuController {

    @FXML private StackPane mainRoot;
    @FXML private Button btnCpu;
    @FXML private Button btnMultiplayer;
    
    public static Difficulty selectedDifficulty = Difficulty.HARD;

    @FXML
    public void initialize() {
        // Ready for any initialization
    }

    @FXML
    void handlePlayCPU(ActionEvent event) {
        DifficultyDialog.show(mainRoot, difficulty -> {
            System.out.println("Starting Single Player with " + difficulty + " difficulty...");
            selectedDifficulty = difficulty;
            BoardController.selectedMode = GameMode.WITH_CPU;
            Navigator.setRoot(Screen.BOARD.getName());
        });
    }

    @FXML
    void handlePlayWithFreind(ActionEvent event) {
        System.out.println("Starting Play With Freind...");
        BoardController.selectedMode = GameMode.WITH_FRIEND;
        Navigator.setRoot(Screen.BOARD.getName());
    }
    
    
    @FXML
    void handleMultiplayer(ActionEvent event) {
        System.out.println("Starting Multiplayer...");
        // Navigate to Login/Register screen
    }
}
