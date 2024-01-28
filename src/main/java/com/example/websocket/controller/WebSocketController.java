package com.example.websocket.controller;

import com.example.websocket.entity.Message;
import com.example.websocket.service.Producer;
import com.example.websocket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*") //when using @RequestMapping, @GetMapping, @PostMapping, ...
public class WebSocketController {
    @Autowired
    private Producer producer;

    /**
     * Nếu không sử dụng @SendTo thì sử dụng SimpMessagingTemplate giống như function ở dưới
     * @param topic
     * @param message
     * @return
     */
    @MessageMapping("/send-to/{topic}") //maybe use @RequestMapping
    @SendTo("/topic/message") //message is sent to /topic/message and then sent to client subscribing the topic
    public String sendToPublicMessage(@PathVariable String topic, @Payload Message message){
        return "[" + DateUtil.formatDate(message.getDate()) + "]: " + message.getSender() + " - " + message.getMessage();
    }

    @PostMapping("/send/{topic}")
    public String sendPublicMessage(@PathVariable String topic, @RequestBody Message message){
        producer.sendPublicMessageTo(topic, message);
        return "[" + DateUtil.formatDate(message.getDate()) + "]: " + message.getSender() + " - " + message.getMessage();
    }

    @MessageMapping("/send-private/{topic}")
    public String sendPrivateMessage(@PathVariable String topic, @Payload Message message){
        producer.sendPrivateMessageTo(message.getReceiver(), topic, message);
        return "[" + DateUtil.formatDate(message.getDate()) + "]: " + message.getSender() + " - " + message.getMessage();
    }
}
