package com.mycompany.xtremeo.client.protocol.envelope;

public class RequestEnvelope<T> {
    private Header header;
    private T body;

    public RequestEnvelope(Header header, T body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public T getBody() {
        return body;
    }
}
