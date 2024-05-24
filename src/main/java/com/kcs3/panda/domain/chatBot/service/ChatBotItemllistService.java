package com.kcs3.panda.domain.chatBot.service;

import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.repository.AuctionProgressItemRepository;
import com.kcs3.panda.domain.chatBot.dto.ChatBotItemListResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatBotItemllistService {

    @Autowired
    private AuctionProgressItemRepository auctionProgressItemRepository;

    public List<ChatBotItemListResponseDto> getChatBotItems() {
        List<AuctionProgressItem> auctionProgressItems = auctionProgressItemRepository.findAll();
        return auctionProgressItems.stream()
                .map(item -> new ChatBotItemListResponseDto(item.getItem().getItemId(), item.getItemTitle()))
                .collect(Collectors.toList());
    }
}
