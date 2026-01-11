/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.xtremeo.client.util;

/**
 *
 * @author Elsobky
 */
public enum Screen {
    SPLASH("splash"),
    MAIN("main-menu"),
    BOARD("board"),
    REGISTER("register"),
    LOGIN("login-ui");
    private final String name;
    Screen(String s){
       name = s;
    }
    public String getName() {
        return name;
    }
}
