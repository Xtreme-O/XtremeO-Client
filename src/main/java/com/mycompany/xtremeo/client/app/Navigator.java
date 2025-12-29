/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.app;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author Elsobky
 */


public class Navigator {
    private static Stage stage;

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void setRoot(String fxml) {
        try {
            Parent root = FXMLLoader.load(
                Navigator.class.getResource("/com/mycompany/xtremeo/client/view/" + fxml)
            );
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

