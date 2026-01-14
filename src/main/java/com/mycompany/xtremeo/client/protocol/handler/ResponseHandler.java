package com.mycompany.xtremeo.client.protocol.handler;


public interface ResponseHandler<T> {
    void handle(String json);
}
