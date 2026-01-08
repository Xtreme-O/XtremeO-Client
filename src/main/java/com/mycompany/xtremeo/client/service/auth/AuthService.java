package com.mycompany.xtremeo.client.service.auth;

import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;

public interface AuthService {
    <T> void send(RequestEnvelope<T> request);
}
