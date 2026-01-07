package com.mycompany.xtremeo.client.network;

import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;

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
        //TODO handle error dialog
        System.out.println("Disconnected from server");
    }
}
