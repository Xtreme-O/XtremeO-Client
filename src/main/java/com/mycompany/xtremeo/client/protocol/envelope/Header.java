package com.mycompany.xtremeo.client.protocol.envelope;

public class Header {
    private String protocol;
    private String action;

    public Header(String protocol, String action) {
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
