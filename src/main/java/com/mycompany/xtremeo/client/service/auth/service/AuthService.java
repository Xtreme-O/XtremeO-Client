package com.mycompany.xtremeo.client.service.auth.service;

import com.mycompany.xtremeo.client.service.auth.model.RequestEnvelope;

public interface AuthService {
    <T> void send(RequestEnvelope<T> request);
}
