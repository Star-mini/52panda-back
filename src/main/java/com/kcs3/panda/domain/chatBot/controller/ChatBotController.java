package com.kcs3.panda.domain.chatBot.controller;

import com.kcs3.panda.domain.chatBot.dto.ChatBotItemListResponseDto;
import com.kcs3.panda.domain.chatBot.service.ChatBotItemllistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/no-auth/chatBot")
public class ChatBotController {

    @Autowired
    private ChatBotItemllistService chatBotItemllistService;

    @GetMapping
    public List<ChatBotItemListResponseDto> getChatBotItems() {
        return chatBotItemllistService.getChatBotItems();
    }
}
