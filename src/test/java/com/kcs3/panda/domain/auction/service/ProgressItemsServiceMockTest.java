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
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
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
        Item item1 = ItemFixture.createProgressItem(user1, category, tradingMethod, region);

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

        //Given
        User user1 = UserFixture.createUser();
        Category category = CategoryFixture.createPhoneCategory();
        TradingMethod tradingMethod = TradingMethodFixture.createBothTradingMethod();
        Region region = RegionFixture.createJungRegion();

        Item item1 = ItemFixture.createCompleteItem(user1, category, tradingMethod, region);

        AuctionCompleteItem auctionCompleteItem = AuctionCompleteItemFixture.createAuctionCompleteItem(item1, user1);
        List<AuctionCompleteItem> auctionProgressItemList = List.of(auctionCompleteItem);

        String status = "completion";
        Pageable pageable = PageRequest.of(0, 10);
        SliceImpl result = new SliceImpl(auctionProgressItemList, pageable, false);

        // Given 에서 만들어진 예측값
        when(itemRepository.findByCompleteItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable)).thenReturn(result);


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
    public void testGetProgressItems() {
        // Given
        User user1 = UserFixture.createUser();
        Category category = CategoryFixture.createPhoneCategory();
        TradingMethod tradingMethod = TradingMethodFixture.createBothTradingMethod();
        Region region = RegionFixture.createJungRegion();
        Item progressItem1 = ItemFixture.createProgressItem(user1, category, tradingMethod, region);
        Item completeItem1 = ItemFixture.createCompleteItem(user1, category, tradingMethod, region);

        AuctionProgressItem progressItem = AuctionProgressItemFixture.createAuctionProgressItem(progressItem1, user1);
        AuctionCompleteItem completeItem = AuctionCompleteItemFixture.createAuctionCompleteItem(completeItem1, user1);

        // Mock 데이터 설정
        Pageable pageable = PageRequest.of(0, 10);
        Slice<AuctionProgressItem> progressItemsSlice = new SliceImpl<>(List.of(progressItem), pageable, false);
        Slice<AuctionCompleteItem> completeItemsSlice = new SliceImpl<>(List.of(completeItem), pageable, false);
        when(itemRepository.findByProgressItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable)).thenReturn(progressItemsSlice);
        when(itemRepository.findByCompleteItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable)).thenReturn(completeItemsSlice);


        // When
        ProgressItemListDto savedProgressItemListDto = progressItemsService.getProgressItems("phone", 2, "jung", "null", pageable);


        // Then
        assertEquals(2, savedProgressItemListDto.progressItemListDto().size()); // 두 개의 아이템이 반환되는지 확인

        ProgressItemsDto progressItemsDto1 = savedProgressItemListDto.progressItemListDto().get(0);
        assertEquals(progressItem1.getItemId(), progressItemsDto1.itemId());
        assertEquals(progressItem.getItemTitle(), progressItemsDto1.itemTitle());
        assertEquals(category.getCategory(), progressItemsDto1.category());
        assertEquals(tradingMethod.getTradingMethod(), progressItemsDto1.tradingMethod());
        assertEquals(progressItem.getStartPrice(), progressItemsDto1.startPrice());
        assertEquals(progressItem.getThumbnail(), progressItemsDto1.thumbnail());
        assertEquals(progressItem.getMaxPrice(), progressItemsDto1.currentPrice());
        assertFalse(progressItemsDto1.isAuctionComplete());

        ProgressItemsDto progressItemsDto2 = savedProgressItemListDto.progressItemListDto().get(1);
        assertEquals(completeItem1.getItemId(), progressItemsDto2.itemId());
        assertEquals(completeItem.getItemTitle(), progressItemsDto2.itemTitle());
        assertEquals(category.getCategory(), progressItemsDto2.category());
        assertEquals(tradingMethod.getTradingMethod(), progressItemsDto2.tradingMethod());
        assertEquals(completeItem.getStartPrice(), progressItemsDto2.startPrice());
        assertEquals(completeItem.getThumbnail(), progressItemsDto2.thumbnail());
        assertEquals(completeItem.getMaxPrice(), progressItemsDto2.currentPrice());
        assertTrue(progressItemsDto2.isAuctionComplete());

        verify(itemRepository, times(1)).findByProgressItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable);
        verify(itemRepository, times(1)).findByCompleteItemWithLocationAndMethodAndRegion("phone", 2, "jung", pageable);
    }






}