/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.xtremeo.client.controller;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.model.game.GameMode;
import com.mycompany.xtremeo.client.ui.dialog.DifficultyDialog;
import com.mycompany.xtremeo.client.ui.dialog.HistoryDialog;
import com.mycompany.xtremeo.client.ui.dialog.RecordGameDialog;
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
    @FXML private Button btnHistory;
    
    @FXML
    public void initialize() {
        // Ready for any initialization
    }

    @FXML
    void handlePlayCPU(ActionEvent event) {
        DifficultyDialog.show(mainRoot, difficulty -> {
            RecordGameDialog.show(mainRoot, record -> {
                BoardController controller = Navigator.setRoot(Screen.BOARD.getName());
                if (controller != null) {
                    controller.init(GameMode.WITH_CPU, difficulty, record);
                }
            });
        });
    }

    @FXML
    void handlePlayWithFreind(ActionEvent event) {
        RecordGameDialog.show(mainRoot, record -> {
            BoardController controller = Navigator.setRoot(Screen.BOARD.getName());
            if (controller != null) {
                controller.init(GameMode.WITH_FRIEND, record);
            }
        });
    }
    
    
    @FXML
    void handleMultiplayer(ActionEvent event) {
        System.out.println("Starting Multiplayer...");
        // Navigate to Login/Register screen
    }

    @FXML
    void handleHistory(ActionEvent event) {
        HistoryDialog.show(mainRoot);
    }
}
