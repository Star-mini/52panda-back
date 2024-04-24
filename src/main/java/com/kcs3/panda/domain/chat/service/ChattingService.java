package com.kcs3.panda.domain.chat.service;

import com.kcs3.panda.domain.chat.dto.ChatMessageDto;
import com.kcs3.panda.domain.chat.dto.ChatRoomDto;
import com.kcs3.panda.domain.chat.entity.ChattingContent;
import com.kcs3.panda.domain.chat.entity.ChattingRoom;
import com.kcs3.panda.domain.chat.repository.ChattingContentRepository;
import com.kcs3.panda.domain.chat.repository.ChattingRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

        ChattingRoom room = chattingRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("ChattingRoom not found with id: " + roomId));

        log.info("사용자"+chatUserId+":"+content);
        ChattingContent chattingContent = ChattingContent.builder()
                .chattingRoom(room)
                .chatUserId(chatUserId)
                .chatContent(content)
                .build();

        chattingContentRepository.save(chattingContent);

    }

    public List<ChatRoomDto> getAllChatRooms(Long userId){
        List<ChattingRoom> chattingRooms = chattingRoomRepository.findByBuyerIdOrSellerId(userId,userId);

        return chattingRooms.stream()
                .map(chattingRoom -> {
                    ChattingContent recentChattingContent = chattingContentRepository.findTopByChattingRoomOrderByCreatedAtDesc(chattingRoom);
                    String recentContent = recentChattingContent != null ? recentChattingContent.getChatContent() : null;
                    LocalDateTime recentDateTime = recentChattingContent != null ? recentChattingContent.getCreatedAt() : null;

                    return ChatRoomDto.builder()
                            .roomId(chattingRoom.getId())
                            .recentContent(recentContent)
                            .recentDateTime(recentDateTime)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<ChatMessageDto> getChatRoomContents(Long roomId){
        List<ChattingContent> chattingContents = chattingContentRepository.findByChattingRoomId(roomId);

        return chattingContents.stream()
                .map(chattingContent -> ChatMessageDto.builder()
                        .chatUser(chattingContent.getChatUserId())
                        .content(chattingContent.getChatContent())
                        .build())
                .collect(Collectors.toList());
    }
}
