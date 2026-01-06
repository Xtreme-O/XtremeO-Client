package com.mycompany.xtremeo.client.service.auth.model;

public class RequestHeader {
    private String protocol;
    private String action;

    public RequestHeader(String protocol, String action) {
        this.protocol = protocol;
        this.action = action;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getAction() {
        return action;
    }
}
