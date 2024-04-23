package com.kcs3.panda.domain.chat.service;

import com.kcs3.panda.domain.chat.dto.ChatMessageDto;
import com.kcs3.panda.domain.chat.entity.ChattingContent;
import com.kcs3.panda.domain.chat.entity.ChattingRoom;
import com.kcs3.panda.domain.chat.repository.ChattingContentRepository;
import com.kcs3.panda.domain.chat.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingContentRepository chattingContentRepository;
    private final ChattingRoomRepository chattingRoomRepository;

    public void saveChatting(Long roomId,ChatMessageDto chatMessageDto){

        Long chatUserId = chatMessageDto.getChatUser();
        String content = chatMessageDto.getContent();

        log.info("사용자"+chatUserId+":"+content);
        ChattingContent chattingContent = ChattingContent.builder()
                .chattingRoom(chattingRoomRepository.findById(roomId).get())
                .chatUserId(chatUserId)
                .chatContent(content)
                .build();

        chattingContentRepository.save(chattingContent);

    }
}
