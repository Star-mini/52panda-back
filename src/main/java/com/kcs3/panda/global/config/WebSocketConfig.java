package com.kcs3.panda.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 웹 소켓 엔드포인트 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // room으로 시작되는 요청을 구독한 모든 사용자들에게 메시지를 broadcast한다.
        registry.enableSimpleBroker("/room");
        // message로 시작되는 메시지는 message-handling methods로 라우팅된다.
        registry.setApplicationDestinationPrefixes("/message");
    }
}
