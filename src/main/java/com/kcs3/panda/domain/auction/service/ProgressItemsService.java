package com.kcs3.panda.domain.auction.service;


import com.kcs3.panda.domain.auction.dto.ProgressItemListDto;
import com.kcs3.panda.domain.auction.dto.ProgressItemsDto;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProgressItemsService {

    private final ItemRepository itemRepository;




    /**
     * 경매진행중인 아이템 목록 조회 - 서비스 로직
     */

    public ProgressItemListDto getProgressItems(String category, Integer method, String region, String status, Pageable pageable) {
        List<ProgressItemsDto> progressItemDtoList = new ArrayList<>();

        if ("progress".equals(status)) {
            Slice<Item> progressItems = itemRepository.findByProgressItemWithLocationAndMethodAndRegion(category, method, region, pageable);
            for(Item item : progressItems) {
                ProgressItemsDto progressItemsDto = ProgressItemsDto.fromEntity(item);
                progressItemDtoList.add(progressItemsDto);
            }
        } else if ("completion".equals(status)) {
            Slice<Item> completionItems = itemRepository.findByCompleteItemWithLocationAndMethodAndRegion(category, method, region, pageable);
            for(Item item : completionItems) {
                ProgressItemsDto progressItemsDto = ProgressItemsDto.fromEntity(item);
                progressItemDtoList.add(progressItemsDto);
            }
        } else if ("everything".equals(status)) {
            Slice<Item> allItems = itemRepository.findByAllItemWithLocationAndMethodAndRegion(category, method, region, pageable);
            for(Item item : allItems) {
                ProgressItemsDto progressItemsDto = ProgressItemsDto.fromEntity(item);
                progressItemDtoList.add(progressItemsDto);
            }
        } else {
            // Handle invalid status
            // 예외 처리 또는 기본적으로 모든 항목을 가져오는 것을 결정할 수 있습니다.
        }

        return ProgressItemListDto.builder()
                .progressItemListDto(progressItemDtoList)
                .build();
    }






}

