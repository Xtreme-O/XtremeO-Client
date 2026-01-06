package com.mycompany.xtremeo.client.service.auth.model;

public class RequestEnvelope<T> {
    private RequestHeader header;
    private T body;

    public RequestEnvelope(RequestHeader header, T body) {
        this.header = header;
        this.body = body;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public T getBody() {
        return body;
    }
}
