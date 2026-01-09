package com.mycompany.xtremeo.client.listener.auth;

import com.mycompany.xtremeo.client.model.common.Player;

public interface RegisterUIListener {
    void onRegisterResponse(Player player);


    default void onRegisterError(String errorMessage) {
        System.err.println("Login error: " + errorMessage);
    }
}
