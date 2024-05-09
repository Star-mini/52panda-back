package com.kcs3.panda.domain.auction.service;

import com.kcs3.panda.domain.auction.dto.ProgressItemListDto;
import com.kcs3.panda.domain.auction.dto.ProgressItemsDto;
import com.kcs3.panda.domain.auction.entity.*;
import com.kcs3.panda.domain.auction.repository.AuctionInfoRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressItemsServiceMockTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ProgressItemsService progressItemsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("진행 중인 경매아이템 목록 조회 - 로직 검증")
    public void saveProgressItem() {
        // Given
        User user1 = UserFixture.createUser();
        Category category = CategoryFixture.createPhoneCategory();
        TradingMethod tradingMethod = TradingMethodFixture.createBothTradingMethod();
        Region region = RegionFixture.createJungRegion();
        Item item1 = ItemFixture.createProgressItem(category, tradingMethod, region);

        AuctionProgressItem auctionProgressItem = AuctionProgressItemFixture.createAuctionProgressItem(item1, user1);
        List<AuctionProgressItem> auctionProgressItemList = List.of(auctionProgressItem);

        String status = "progress";
        Pageable pageable = PageRequest.of(0, 10);
        SliceImpl result = new SliceImpl(auctionProgressItemList, pageable, false);

        // Given 에서 만들어진 예측값
        when(itemRepository.findByProgressItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable)).thenReturn(result);


        // When -> 실제 serivce 메서드 호출하여 예측값과 비교한다.
        ProgressItemListDto savedProgressItemListDto = progressItemsService.getProgressItems("phone",2,"jung","progress", pageable);
        ProgressItemsDto progressItemsDto = savedProgressItemListDto.progressItemListDto().get(0);


        // Then
        assertEquals(item1.getItemId(), progressItemsDto.itemId());
        assertEquals(auctionProgressItem.getItemTitle(), progressItemsDto.itemTitle());
        assertEquals(category.getCategory(), progressItemsDto.category());
        assertEquals(tradingMethod.getTradingMethod(), progressItemsDto.tradingMethod());
        assertEquals(auctionProgressItem.getStartPrice(), progressItemsDto.startPrice());
        assertEquals(auctionProgressItem.getThumbnail(), progressItemsDto.thumbnail());
        assertEquals(auctionProgressItem.getMaxPrice(), progressItemsDto.currentPrice());
        assertEquals(item1.isAuctionComplete(), progressItemsDto.isAuctionComplete());

        verify(itemRepository, times(1)).findByProgressItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable);
        verify(itemRepository, times(0)).findByCompleteItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable);


    }




    @Test
    @DisplayName("완료된 경매아이템 목록 조회 - 로직 검증")
    public void saveCompleteItem() {
        User user1 = UserFixture.createUser();
        Category category = CategoryFixture.createPhoneCategory();
        TradingMethod tradingMethod = TradingMethodFixture.createBothTradingMethod();
        Region region = RegionFixture.createJungRegion();

        Item item1 = ItemFixture.createCompleteItem(category, tradingMethod, region);


        AuctionCompleteItem auctionCompleteItem = AuctionCompleteItemFixture.createAuctionCompleteItem(item1, user1);
        List<AuctionCompleteItem> auctionProgressItemList = List.of(auctionCompleteItem);

        String status = "completion";
        Pageable pageable = PageRequest.of(0, 10);
        SliceImpl result = new SliceImpl(auctionProgressItemList, pageable, false);

        // Given 에서 만들어진 예측값
        when(itemRepository.findByProgressItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable)).thenReturn(result);


        // When -> 실제 serivce 메서드 호출하여 예측값과 비교한다.
        ProgressItemListDto savedProgressItemListDto = progressItemsService.getProgressItems("phone",2,"jung","completion", pageable);


        ProgressItemsDto progressItemsDto = savedProgressItemListDto.progressItemListDto().get(0);

        // Then
        assertEquals(item1.getItemId(), progressItemsDto.itemId());
        assertEquals(auctionCompleteItem.getItemTitle(), progressItemsDto.itemTitle());
        assertEquals(category.getCategory(), progressItemsDto.category());
        assertEquals(tradingMethod.getTradingMethod(), progressItemsDto.tradingMethod());
        assertEquals(auctionCompleteItem.getStartPrice(), progressItemsDto.startPrice());
        assertEquals(auctionCompleteItem.getThumbnail(), progressItemsDto.thumbnail());
        assertEquals(auctionCompleteItem.getMaxPrice(), progressItemsDto.currentPrice());
        assertEquals(item1.isAuctionComplete(), progressItemsDto.isAuctionComplete());

        verify(itemRepository, times(0)).findByProgressItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable);
        verify(itemRepository, times(1)).findByCompleteItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable);

    }


    @Test
    @DisplayName("전체 경매아이템 목록 조회 - 로직 검증")
    public void saveAllItem() {
        User user1 = UserFixture.createUser();
        Category category = CategoryFixture.createPhoneCategory();
        TradingMethod tradingMethod = TradingMethodFixture.createBothTradingMethod();
        Region region = RegionFixture.createJungRegion();
        Item progressItem = ItemFixture.createProgressItem(category, tradingMethod, region);
        Item completeItem = ItemFixture.createCompleteItem(category, tradingMethod, region);

        AuctionCompleteItem auctionCompleteItem = AuctionCompleteItemFixture.createAuctionCompleteItem(progressItem, user1);
        AuctionCompleteItem auctionCompleteItem = AuctionCompleteItemFixture.createAuctionCompleteItem(completeItem, user1);
        List<AuctionCompleteItem> auctionProgressItemList = List.of(auctionCompleteItem);

        String status = "completion";
        Pageable pageable = PageRequest.of(0, 10);
        SliceImpl result = new SliceImpl(auctionProgressItemList, pageable, false);

        // Given 에서 만들어진 예측값
        when(itemRepository.findByProgressItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable)).thenReturn(result);


        // When -> 실제 serivce 메서드 호출하여 예측값과 비교한다.
        ProgressItemListDto savedProgressItemListDto = progressItemsService.getProgressItems("phone",2,"jung","completion", pageable);


        ProgressItemsDto progressItemsDto = savedProgressItemListDto.progressItemListDto().get(0);

        // Then
        assertEquals(item1.getItemId(), progressItemsDto.itemId());
        assertEquals(auctionCompleteItem.getItemTitle(), progressItemsDto.itemTitle());
        assertEquals(category.getCategory(), progressItemsDto.category());
        assertEquals(tradingMethod.getTradingMethod(), progressItemsDto.tradingMethod());
        assertEquals(auctionCompleteItem.getStartPrice(), progressItemsDto.startPrice());
        assertEquals(auctionCompleteItem.getThumbnail(), progressItemsDto.thumbnail());
        assertEquals(auctionCompleteItem.getMaxPrice(), progressItemsDto.currentPrice());
        assertEquals(item1.isAuctionComplete(), progressItemsDto.isAuctionComplete());

        verify(itemRepository, times(0)).findByProgressItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable);
        verify(itemRepository, times(1)).findByCompleteItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable);

    }



}