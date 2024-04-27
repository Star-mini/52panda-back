package com.kcs3.panda.domain.auction.service;


import com.kcs3.panda.domain.auction.dto.ProgressItemListDto;
import com.kcs3.panda.domain.auction.dto.ProgressItemsDto;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressItemsService {

    private final ItemRepository itemRepository;




    /**
     * 경매진행중인 아이템 목록 조회 - 서비스 로직
     */
    public ProgressItemListDto getProgressItems(String category, Integer method, String region, Pageable pageable) {
        Slice<Item> progressItems = itemRepository.findByProgressItemsWithLocationAndMethodAndRegion(category, method, region, pageable);
        List<ProgressItemsDto> progressItemDtoList = new ArrayList<>();

        for(Item item : progressItems) {
            ProgressItemsDto progressItemsDto = ProgressItemsDto.fromEntity(item);
            progressItemDtoList.add(progressItemsDto);
        }


        return ProgressItemListDto.builder()
                .progressItemListDto(progressItemDtoList)
                .build();
    }


    /**
     * 경매완료된 아이템 목록 조회 - 서비스 로직
     */
    public ProgressItemListDto getCompletionsItems(String category, Integer method, String region, Pageable pageable) {
        Slice<Item> completionItems = itemRepository.findByCompleteItemsWithLocationAndMethodAndRegion(category, method, region, pageable);
        List<ProgressItemsDto> completionItemDtoList = new ArrayList<>();

        for(Item item : completionItems) {
            ProgressItemsDto progressItemsDto = ProgressItemsDto.fromEntity(item);
            completionItemDtoList.add(progressItemsDto);
        }


        return ProgressItemListDto.builder()
                .progressItemListDto(completionItemDtoList)
                .build();
    }


    /**
     * 모든 경매 아이템 목록 조회 - 서비스 로직
     */
    public ProgressItemListDto getAllItems(String category, Integer method, String region, Pageable pageable) {
        Slice<Item> allItems = itemRepository.findByAllItemsWithLocationAndMethodAndRegion(category, method, region, pageable);
        List<ProgressItemsDto> allItemDtoList = new ArrayList<>();

        for(Item item : allItems) {
            ProgressItemsDto progressItemsDto = ProgressItemsDto.fromEntity(item);
            allItemDtoList.add(progressItemsDto);
        }


        return ProgressItemListDto.builder()
                .progressItemListDto(allItemDtoList)
                .build();
    }



}

