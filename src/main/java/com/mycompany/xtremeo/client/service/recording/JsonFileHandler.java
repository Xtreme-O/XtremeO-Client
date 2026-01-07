package com.mycompany.xtremeo.client.service.recording;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;

import java.io.*;
import java.time.LocalDateTime;

public class JsonFileHandler {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public void save(Object obj, File file) throws IOException {
        try (FileWriter writer = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(writer)) {
            gson.toJson(obj, bw);
        }
    }

    public <T> T load(File file, Class<T> type) throws IOException {
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            return gson.fromJson(br, type);
        }
    }
}