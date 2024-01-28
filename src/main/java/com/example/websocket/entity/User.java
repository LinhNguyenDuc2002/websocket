package com.example.websocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
public class User implements Principal {
    private String username;

    @Override
    public String getName() {
        return username;
    }
}
