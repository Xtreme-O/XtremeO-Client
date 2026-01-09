package com.mycompany.xtremeo.client.listener.auth;

import com.mycompany.xtremeo.client.model.common.Player;

public interface LoginUIListener {
    void onLoginResponse(Player player);


    default void onLoginError(String errorMessage) {
        System.err.println("Login error: " + errorMessage);
    }
}
