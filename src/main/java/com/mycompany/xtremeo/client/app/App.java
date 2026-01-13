package com.mycompany.xtremeo.client.app;

import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private static final String APP_NAME = "XtremO";
    @Override
    public void start(Stage stage) {
        stage.setTitle(APP_NAME);
        Navigator.init(stage);
        Navigator.setRoot(Screen.SPLASH.getName());
        stage.show();
        stage.setOnCloseRequest((e) -> {
            if(ClientConnection.isConnected()){
                ClientConnection.getInstance().disconnect();
            }
        });
    }


    public static void main(String[] args) {
        launch();
    }
}