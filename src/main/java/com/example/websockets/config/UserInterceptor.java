package com.example.websockets.config;

import com.example.websockets.entity.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.ArrayList;
import java.util.Map;

public class UserInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // Check if the message is a CONNECT command
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Retrieve the raw headers from the message
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

            // Check if the raw headers is an instance of Map
            if (raw instanceof Map) {
                // Retrieve the value associated with the key "username"
                Object name = ((Map) raw).get("username");

                // Check if the value is an ArrayList
                if (name instanceof ArrayList) {
                    // Set the user in the StompHeaderAccessor using the first element of the ArrayList
                    accessor.setUser(new User(((ArrayList<String>) name).get(0).toString()));
                }
            }
        }
        return message; // Return the modified message
    }
}
