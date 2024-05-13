package com.kcs3.panda.domain.chat.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomDto {
    private Long roomId;
    private String chatTitle;
    private String recentContent;
    private String username;
    private LocalDateTime recentDateTime;
}
