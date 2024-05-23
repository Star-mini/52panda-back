package com.kcs3.panda.domain.chat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kcs3.panda.domain.chat.dto.ChatMessageDto;
import com.kcs3.panda.domain.chat.entity.ChattingContent;
import com.kcs3.panda.domain.chat.entity.ChattingRoom;
import com.kcs3.panda.domain.chat.repository.ChattingContentRepository;
import com.kcs3.panda.domain.chat.repository.ChattingRoomRepository;
import com.kcs3.panda.domain.chat.service.ChattingService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChattingServiceTest {

    @InjectMocks
    private ChattingService chattingService;

    @Mock
    private ChattingRoomRepository chattingRoomRepository;

    @Mock
    private ChattingContentRepository chattingContentRepository;


    @Test
    public void testSaveChatting(){
        Long roomId = 1L;
        String chatMessage = "채팅기록 저장 테스트";

        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                        .chatUser(roomId)
                        .content(chatMessage)
                        .build();

        ChattingRoom room = new ChattingRoom();

        when(chattingRoomRepository.findById(roomId)).thenReturn(Optional.of(room));

        chattingService.saveChatting(roomId,chatMessageDto);

        verify(chattingContentRepository, times(1)).save(any(ChattingContent.class));
    }

    @Test
    public void testGetAllChatRooms(){
        Long buyerId = 123L;
        Long sellerId = 456L;

        ChattingRoom chattingRoom1 = ChattingRoom.builder()
                .buyerId(buyerId)
                .sellerId(sellerId)
                .build();

        ChattingRoom chattingRoom2 = ChattingRoom.builder()
                .buyerId(buyerId)
                .sellerId(sellerId)
                .build();


    }

}
