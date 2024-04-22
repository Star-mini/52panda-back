package com.kcs3.panda.domain.chat.controller;

import com.kcs3.panda.domain.chat.dto.ChatMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    @MessageMapping("/{roomId}")
    @SendTo("/room/{roomId}")
    public ChatMessageDto sendMessage(@DestinationVariable("roomId") Long roomId, @Payload ChatMessageDto chatMessageDto) {
        log.info("웹소켓 테스트"+chatMessageDto);

        return chatMessageDto;
    }
}
