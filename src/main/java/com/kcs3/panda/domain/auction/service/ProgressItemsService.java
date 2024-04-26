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
        Slice<Item> ProgressItems = itemRepository.findByProgressItemsWithLocationAndmethodAndRegion(category, method, region, pageable);
        List<ProgressItemsDto> progressItemDtoList = new ArrayList<>();

        for(Item item : ProgressItems) {
            ProgressItemsDto progressItemsDto = ProgressItemsDto.fromEntity(item);
            progressItemDtoList.add(progressItemsDto);
        }


        return ProgressItemListDto.builder()
                .progressItemListDto(progressItemDtoList)
                .build();
    }


}

