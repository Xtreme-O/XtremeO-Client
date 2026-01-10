package com.mycompany.xtremeo.client.app;

import com.mycompany.xtremeo.client.util.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static final String APP_NAME = "XtremO";
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle(APP_NAME);
        Navigator.init(stage);
        Navigator.setRoot(Screen.SPLASH.getName());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}