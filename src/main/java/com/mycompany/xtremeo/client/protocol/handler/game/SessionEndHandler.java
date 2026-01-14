package com.mycompany.xtremeo.client.protocol.handler.game;

import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.game.InviteConfirmationBody;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import com.mycompany.xtremeo.client.util.GsonProvider;

import java.util.function.Consumer;

public class SessionEndHandler implements ResponseHandler<Void> {

    private static Consumer<Void> onSessionEnded;

    public static void onSessionEnded(Runnable action) {
        onSessionEnded = v -> action.run();
    }

    @Override
    public void handle(String json) {
        if (onSessionEnded != null) {
            onSessionEnded.accept(null);
        }
    }
}
