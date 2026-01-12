package com.mycompany.xtremeo.client.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.network.DispatcherMessageListener;
import com.mycompany.xtremeo.client.network.NetworkConfig;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;
import com.mycompany.xtremeo.client.service.SocketRequestSender;
import com.mycompany.xtremeo.client.service.auth.LoginService;
import com.mycompany.xtremeo.client.util.GsonProvider;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

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
        test();
    }

    void test(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ClientConnection connection = ClientConnection.getInstance();
        ResponseDispatcher dispatcher = new ResponseDispatcher(gson);
        connection.connect(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
        connection.startListening(
                new DispatcherMessageListener(dispatcher)
        );
//        LoginService service = new LoginService(new SocketRequestSender(gson,connection));
//        service.login("sobky","123");
        System.out.println("Client connected and listening...");
    }

    public static void main(String[] args) {
        launch();
    }
}