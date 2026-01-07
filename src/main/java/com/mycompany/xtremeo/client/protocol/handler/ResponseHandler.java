package com.mycompany.xtremeo.client.protocol.handler;

import com.google.gson.Gson;

public interface ResponseHandler<T> {
    void handle(String json, Gson gson);
}
