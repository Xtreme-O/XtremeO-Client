/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.app;


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

    public static void setRoot(String screen) {
        try {
            String basePath = "/com/mycompany/xtremeo/client/view/";
            String fxmlPath = basePath + screen + ".fxml";
            String cssPath = basePath + "styles/" + screen +".css";
            Parent root = FXMLLoader.load(
                Navigator.class.getResource(fxmlPath)
            );
            Scene scene = new Scene(root);
            stage.setWidth(1200);// by mona
            stage.setHeight(800);// by mona
            stage.setResizable(true);//by mona
            scene.getStylesheets().add(
                Navigator.class.getResource(cssPath).toExternalForm()
            );
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

