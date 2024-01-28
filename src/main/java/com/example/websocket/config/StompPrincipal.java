package com.example.websocket.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@AllArgsConstructor
@Data
public class StompPrincipal implements Principal {
    private String id;
    @Override
    public String getName() {
        return id;
    }
}
