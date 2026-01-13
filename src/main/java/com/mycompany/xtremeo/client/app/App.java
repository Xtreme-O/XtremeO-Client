package com.mycompany.xtremeo.client.app;

import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.network.DispatcherMessageListener;
import com.mycompany.xtremeo.client.network.NetworkConfig;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;
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
    public void start(Stage stage) {
        stage.setTitle(APP_NAME);
        Navigator.init(stage);
        Navigator.setRoot(Screen.SPLASH.getName());
        stage.show();
        test();
    }

    void test(){
        ClientConnection connection = ClientConnection.getInstance();
        ResponseDispatcher dispatcher = new ResponseDispatcher();
        connection.connect(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
        connection.startListening(
                new DispatcherMessageListener(dispatcher)
        );
        System.out.println("Client connected and listening...");
    }

    public static void main(String[] args) {
        launch();
    }
}