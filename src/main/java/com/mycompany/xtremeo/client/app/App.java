package com.mycompany.xtremeo.client.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.listener.auth.LoginUIListener;
import com.mycompany.xtremeo.client.listener.auth.RegisterUIListener;
import com.mycompany.xtremeo.client.model.common.Player;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.network.DispatcherMessageListener;
import com.mycompany.xtremeo.client.network.NetworkConfig;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Navigator.init(stage);
        Navigator.setRoot(Screen.SPLASH.getName());
        stage.show();
        test();
    }

    void test(){
        LoginUIListener loginListener = new LoginUIListener() {
            @Override
            public void onLoginResponse(Player player) {
                System.out.println("Login Successfully from UI: " + player.toString());
            }

            @Override
            public void onLoginError(String errorMessage) {
                LoginUIListener.super.onLoginError(errorMessage);
            }
        };
        RegisterUIListener registerListener = new RegisterUIListener() {

            @Override
            public void onRegisterResponse(Player player) {
                System.out.println("Register Successfully from UI: " + player.toString());
            }

            @Override
            public void onRegisterError(String errorMessage) {
                RegisterUIListener.super.onRegisterError(errorMessage);
            }
        };
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ResponseDispatcher dispatcher = new ResponseDispatcher(gson,
                loginListener, registerListener);
        ClientConnection connection = new ClientConnection();
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