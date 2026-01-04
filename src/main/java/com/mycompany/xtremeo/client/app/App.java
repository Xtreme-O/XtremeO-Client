package com.mycompany.xtremeo.client.app;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Navigator.init(stage);
        Navigator.setRoot("splash.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}