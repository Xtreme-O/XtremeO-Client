package com.mycompany.xtremeo.client.service;

import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;

@FunctionalInterface
public interface RequestSender {
    void send(RequestEnvelope<?> request);
}
