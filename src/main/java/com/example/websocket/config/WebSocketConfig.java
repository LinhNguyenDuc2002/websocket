package com.example.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //enable WebSocket Server
/**
 * extends AbstractWebSocketMessageBrokerConfigurer to override methods to customize the protocols and endpoints
 */
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * This method will configure the message broker options
     * In this case, it will enable the broker in the /topic endpoint.
     * This means that the clients who want to use the WebSockets broker need to use the /topic to connect
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); //carry messages back to client
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/users");
    }

    /**
     * This method will register the Stomp (https://stomp.github.io/) endpoint
     * In this case it will register the /ws endpoint and use the JavaScript library SockJS (https://github.com/sockjs)
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").setAllowedOrigins("http://127.0.0.1:5500").withSockJS();
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .withSockJS();
    }
}
