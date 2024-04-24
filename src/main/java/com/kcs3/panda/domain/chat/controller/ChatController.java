package com.kcs3.panda.domain.chat.controller;

import com.kcs3.panda.domain.chat.dto.ChatMessageDto;
import com.kcs3.panda.domain.chat.dto.ChatRoomDto;
import com.kcs3.panda.domain.chat.service.ChattingService;
import com.kcs3.panda.global.dto.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChattingService chattingService;

    @GetMapping("/rooms")
    public ResponseDto<List<ChatRoomDto>> getAllChatRooms(@RequestParam("userId") Long userId) {
        List<ChatRoomDto> chatRooms = chattingService.getAllChatRooms(userId);
        return ResponseDto.ok(chatRooms);
    }

    @GetMapping("/room/content")
    public ResponseDto<List<ChatMessageDto>> getChatRoomContents(@RequestParam("roomId") Long roomId) {
        List<ChatMessageDto> chatContents = chattingService.getChatRoomContents(roomId);
        return ResponseDto.ok(chatContents);
    }

}
