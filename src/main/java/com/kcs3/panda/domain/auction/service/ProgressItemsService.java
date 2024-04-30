package com.kcs3.panda.domain.auction.service;


import com.kcs3.panda.domain.auction.dto.ProgressItemListDto;
import com.kcs3.panda.domain.auction.dto.ProgressItemsDto;
import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class ProgressItemsService {

    private final ItemRepository itemRepository;


    /**
     * 경매진행중인 아이템 목록 조회 - 서비스 로직
     * 경매완료된 아이템 목록 조회 - 서비스 로직
     * 모든 아이템 목록 조회 - 서비스 로직
     */

    public ProgressItemListDto getProgressItems(String category, Integer method, String region, String status, Pageable pageable) {
        List<ProgressItemsDto> itemtemDtoList = new ArrayList<>();

        if ("progress".equals(status)) {
            Slice<AuctionProgressItem> progressItems = itemRepository.findByProgressItemWithLocationAndMethodAndRegion(category, method, region, pageable);
            log.info("테스트1");
            for (AuctionProgressItem progressItem : progressItems) {
                ProgressItemsDto progressItemsDto = ProgressItemsDto.fromProgressEntity(progressItem);
                itemtemDtoList.add(progressItemsDto);
                log.info("테스트2");
            }
        } else if ("completion".equals(status)) {
            Slice<AuctionCompleteItem> completionItems = itemRepository.findByCompleteItemWithLocationAndMethodAndRegion(category, method, region, pageable);
            for (AuctionCompleteItem completionItem : completionItems) {
                ProgressItemsDto progressItemsDto = ProgressItemsDto.fromCompletionEntity(completionItem);
                itemtemDtoList.add(progressItemsDto);
            }
        } else {
            Slice<AuctionProgressItem> allItems = itemRepository.findByProgressItemWithLocationAndMethodAndRegion(category, method, region, pageable);
            for (AuctionProgressItem progressItems : allItems) {
                ProgressItemsDto progressItemsDto = ProgressItemsDto.fromProgressEntity(progressItems);
                itemtemDtoList.add(progressItemsDto);
            }

            Slice<AuctionCompleteItem> completionItems = itemRepository.findByCompleteItemWithLocationAndMethodAndRegion(category, method, region, pageable);
            for (AuctionCompleteItem completionItem : completionItems) {
                ProgressItemsDto progressItemsDto = ProgressItemsDto.fromCompletionEntity(completionItem);
                itemtemDtoList.add(progressItemsDto);
            }




        } //else

        log.info("테스트3");
        return ProgressItemListDto.builder()
                .progressItemListDto(itemtemDtoList)
                .build();
    }
}

