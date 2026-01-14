package com.mycompany.xtremeo.client.network;

import com.mycompany.xtremeo.client.app.Navigator;
import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;
import com.mycompany.xtremeo.client.service.lobby.MatchmakingService;
import com.mycompany.xtremeo.client.ui.dialog.ErrorDialog;
import com.mycompany.xtremeo.client.util.Screen;
import javafx.application.Platform;

public class DispatcherMessageListener implements MessageListener {

    private final ResponseDispatcher dispatcher;

    public DispatcherMessageListener(ResponseDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void onMessage(String msg) {
        dispatcher.dispatch(msg);
    }

    @Override
    public void onDisconnect(Exception e) {
        Platform.runLater(() -> {
            MatchmakingService.getInstance().clear();
            Navigator.setRoot(Screen.MAIN.getName());
            ErrorDialog.showServerError("An unknown error occurred on the server");
        });
        System.out.println("Disconnected from server");
    }
}
