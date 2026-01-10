package com.mycompany.xtremeo.client.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mycompany.xtremeo.client.ai.Difficulty;
import java.io.IOException;

public class DifficultyAdapter extends TypeAdapter<Difficulty> {

    @Override
    public void write(JsonWriter out, Difficulty value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.name());
        }
    }

    @Override
    public Difficulty read(JsonReader in) throws IOException {
        return Difficulty.fromString(in.nextString());
    }
}
