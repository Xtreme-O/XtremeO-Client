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

import com.mycompany.xtremeo.client.ui.ComponentFactory;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

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
    void handlePlayWithFriend(ActionEvent event) {
        RecordGameDialog.show(mainRoot, record -> {
            BoardController controller = Navigator.setRoot(Screen.BOARD.getName());
            if (controller != null) {
                controller.init(GameMode.WITH_FRIEND, record);
            }
        });
    }
    
    
    @FXML
    void handleMultiplayer(ActionEvent event) {
        RotateTransition animation = showLoading();
        Navigator.setRootAsync(Screen.LOBBY.getName(), e -> hideLoading(animation));
    }

    private RotateTransition showLoading() {
        btnMultiplayer.setDisable(true);
        btnMultiplayer.getStyleClass().add("loading");
        btnMultiplayer.setText("CONNECTING...");

        Arc spinner = ComponentFactory.createSpinner(16, Color.web("#333333"));
        btnMultiplayer.setGraphic(spinner);

        RotateTransition rotate = ComponentFactory.createSpinAnimation(spinner);
        rotate.play();
        return rotate;
    }

    private void hideLoading(RotateTransition animation) {
        animation.stop();
        btnMultiplayer.setGraphic(null);
        btnMultiplayer.setText("MULTIPLAYER");
        btnMultiplayer.getStyleClass().remove("loading");
        btnMultiplayer.setDisable(false);
    }

    @FXML
    void handleHistory(ActionEvent event) {
        HistoryDialog.show(mainRoot);
    }
}
