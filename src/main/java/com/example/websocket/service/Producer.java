package com.example.websocket.service;

import com.example.websocket.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class Producer {
    /**
     * SimpMessagingTemplate is an implementation of the SimpMessagesSendingOperations class
     * that provides methods for sending message to users
     */
    @Autowired
    private SimpMessagingTemplate template;

    private Set<String> client = new LinkedHashSet<>();

    /**
     * This method requires a destination, in this case the topic where the message will be sent.
     * You may have noticed that there is a /topic path before the topic’s name.
     * This is the way WebSockets will identify the topic name, by adding the /topic prefix
     * @param topic
     * @param message
     */
    public void sendPublicMessageTo(String topic, Message message) {
        this.template.convertAndSend("/topic/" + topic, message);
    }

    public void sendPrivateMessageTo(Message message) {
        client.stream().forEach(c -> {
            this.template.convertAndSendToUser(c,"/queue/message", message);
        });
    }

    public void addClient(String id) {
        client.add(id);
    }
}
