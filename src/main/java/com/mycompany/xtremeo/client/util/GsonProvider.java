/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.DifficultyAdapter;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.ai.Difficulty;
import java.time.LocalDateTime;

/**
 *
 * @author wahid
 */
public class GsonProvider {

    private static Gson instance;

    private GsonProvider() {

    }

    public static Gson getGsonProvider() {
        if (instance == null) {
            instance = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .registerTypeAdapter(Difficulty.class, new DifficultyAdapter()).create();
        }

        return instance;

    }

}
