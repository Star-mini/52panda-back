package com.kcs3.panda.domain.auction.service;


import com.kcs3.panda.domain.auction.dto.HotItemListDto;
import com.kcs3.panda.domain.auction.dto.HotItemsDto;
import com.kcs3.panda.domain.auction.dto.ProgressItemListDto;
import com.kcs3.panda.domain.auction.dto.ProgressItemsDto;
import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.repository.AuctionInfoRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class ProgressItemsService {

    private final ItemRepository itemRepository;
    private final AuctionInfoRepository auctionInfoRepository;


    private final RedisTemplate<String, HotItemsDto> redisTemplate;

    /**
     * 경매진행중인 아이템 목록 조회 - 서비스 로직
     * 경매완료된 아이템 목록 조회 - 서비스 로직
     * 모든 아이템 목록 조회 - 서비스 로직
     */

    public ProgressItemListDto getProgressItems(String category, Integer method, String region, String status, Pageable pageable) {
        List<ProgressItemsDto> itemtemDtoList = new ArrayList<>();

        if ("progress".equals(status)) {
            Slice<AuctionProgressItem> progressItems = itemRepository.findByProgressItemWithLocationAndMethodAndRegion(category, method, region, pageable);
            for (AuctionProgressItem progressItem : progressItems) {
                ProgressItemsDto progressItemsDto = ProgressItemsDto.fromProgressEntity(progressItem);
                itemtemDtoList.add(progressItemsDto);
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

        return ProgressItemListDto.builder()
                .progressItemListDto(itemtemDtoList)
                .build();
    }



    /**
     * 핫아이템 Redis 저장 서비스 로직
     */

    public HotItemListDto getHotItems(){

        List<HotItemsDto> hotItemsDtos = new ArrayList<>();

        for (int i=1;i<=10;i++) {
            hotItemsDtos.add( redisTemplate.opsForValue().get("hot_item:"+i));
        }

        return HotItemListDto.builder()
                .hotItemListDtos(hotItemsDtos)
                .build();
    }
    public void saveHotItems() {

        // 최근 인기 아이템의 itemId 리스트 조회
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> hotItemIdList = auctionInfoRepository.findTop10ItemIds(pageable);
        List<AuctionProgressItem> hotItemList = new ArrayList<>();


        for (Long itemId : hotItemIdList) {
            AuctionProgressItem hotItem = itemRepository.findByHotItemList(itemId);
            hotItemList.add(hotItem);
        }



        // 조회된 AuctionProgressItem을 HotItemsDto로 변환
        List<HotItemsDto> hotItemsDtos = hotItemList
                .stream()
                .map(HotItemsDto::fromHotEntity)
                .collect(Collectors.toList());


        HotItemListDto hotItemListDto = HotItemListDto.builder()
                .hotItemListDtos(hotItemsDtos)
                .build();

        int i = 1;
        for (HotItemsDto hotItemsDto : hotItemListDto.hotItemListDtos()) {
            redisTemplate.opsForValue().set("hot_item:" + i, hotItemsDto);
            i++;
        }


    }









}

