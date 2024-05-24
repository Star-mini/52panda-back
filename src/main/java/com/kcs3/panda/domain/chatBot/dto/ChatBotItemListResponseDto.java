package com.kcs3.panda.domain.chatBot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotItemListResponseDto {
    private Long itemId;
    private String title;
}
