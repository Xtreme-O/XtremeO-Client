package com.mycompany.xtremeo.client.model.auth.request;

public record RegisterRequestBody(
        String username,
        String password,
        String avatarUrl) { }
