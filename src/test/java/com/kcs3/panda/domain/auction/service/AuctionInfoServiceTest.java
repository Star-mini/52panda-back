package com.kcs3.panda.domain.auction.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kcs3.panda.domain.auction.dto.AuctionInfoSummeryDto;
import com.kcs3.panda.domain.auction.dto.AuctionInfosDto;
import com.kcs3.panda.domain.auction.dto.AuctionPriceDto;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.repository.AuctionCompleteItemRepository;
import com.kcs3.panda.domain.auction.repository.AuctionInfoRepository;
import com.kcs3.panda.domain.auction.repository.AuctionProgressItemRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import com.kcs3.panda.global.exception.CommonException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuctionInfoServiceTest {
    @Mock
    private AuctionProgressItemRepository auctionProgressItemRepo;
    @Mock
    private AuctionCompleteItemRepository auctionCompleteItemRepo;
    @Mock
    private AuctionInfoRepository auctionInfoRepo;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private AuctionInfoServiceImpl auctionInfoService;

    private static final Long ITEM_ID = 1L;
    private static final Long USER_ID = 2L;

    @Test
    void getAuctionInfosDto_AuctionIsInProgress_success() {
        Item mockItem = mock(Item.class);
        when(mockItem.isAuctionComplete()).thenReturn(false);
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(mockItem));

        AuctionPriceDto mockPriceDto = new AuctionPriceDto(100, 1000);
        when(auctionProgressItemRepo.findPriceByItemItemId(ITEM_ID)).thenReturn(Optional.of(mockPriceDto));

        List<AuctionInfoSummeryDto> auctionInfoSummaries = Arrays.asList(new AuctionInfoSummeryDto("Bidder1", 500));
        when(auctionInfoRepo.findInfoSummariesByItemId(ITEM_ID)).thenReturn(auctionInfoSummaries);

        Optional<AuctionInfosDto> result = auctionInfoService.getAuctionInfosDto(ITEM_ID);

        assertTrue(result.isPresent());
        AuctionInfosDto auctionInfosDto = result.get();

        assertEquals(100, auctionInfosDto.buyNowPrice());
        assertEquals(1000, auctionInfosDto.maxPrice());
        assertFalse(auctionInfosDto.info().isEmpty());
        assertEquals("Bidder1", auctionInfosDto.info().get(0).name());
        assertEquals(500, auctionInfosDto.info().get(0).price());

        verify(auctionProgressItemRepo, times(1)).findPriceByItemItemId(ITEM_ID);
        verify(auctionInfoRepo, times(1)).findInfoSummariesByItemId(ITEM_ID);
    }

    @Test
    void getAuctionInfosDto_AuctionIsComplete_success() {
        Item mockItem = mock(Item.class);
        when(mockItem.isAuctionComplete()).thenReturn(true);
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(mockItem));

        AuctionPriceDto mockPriceDto = new AuctionPriceDto(200, 2000);
        when(auctionCompleteItemRepo.findPriceByItemItemId(ITEM_ID)).thenReturn(Optional.of(mockPriceDto));

        List<AuctionInfoSummeryDto> auctionInfoSummaries = Arrays.asList(new AuctionInfoSummeryDto("Bidder2", 1500));
        when(auctionInfoRepo.findInfoSummariesByItemId(ITEM_ID)).thenReturn(auctionInfoSummaries);

        Optional<AuctionInfosDto> result = auctionInfoService.getAuctionInfosDto(ITEM_ID);

        assertTrue(result.isPresent());
        AuctionInfosDto auctionInfosDto = result.get();

        assertEquals(200, auctionInfosDto.buyNowPrice());
        assertEquals(2000, auctionInfosDto.maxPrice());
        assertFalse(auctionInfosDto.info().isEmpty());
        assertEquals("Bidder2", auctionInfosDto.info().get(0).name());
        assertEquals(1500, auctionInfosDto.info().get(0).price());

        verify(auctionCompleteItemRepo, times(1)).findPriceByItemItemId(ITEM_ID);
        verify(auctionInfoRepo, times(1)).findInfoSummariesByItemId(ITEM_ID);
    }

    @Test
    void getAuctionInfosDto_EmptyAuctionInfoSummaries_success() {
        Item mockItem = mock(Item.class);
        when(mockItem.isAuctionComplete()).thenReturn(false);
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(mockItem));

        AuctionPriceDto mockPriceDto = new AuctionPriceDto(100, 1000);
        when(auctionProgressItemRepo.findPriceByItemItemId(ITEM_ID)).thenReturn(Optional.of(mockPriceDto));

        when(auctionInfoRepo.findInfoSummariesByItemId(ITEM_ID)).thenReturn(Arrays.asList());

        Optional<AuctionInfosDto> result = auctionInfoService.getAuctionInfosDto(ITEM_ID);

        assertTrue(result.isPresent());
        assertEquals(100, result.get().buyNowPrice());
        assertEquals(1000, result.get().maxPrice());
        assertTrue(result.get().info().isEmpty());

        verify(auctionProgressItemRepo, times(1)).findPriceByItemItemId(ITEM_ID);
        verify(auctionInfoRepo, times(1)).findInfoSummariesByItemId(ITEM_ID);
    }

    @Test
    void getAuctionInfosDto_ItemNotFound_fail() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.empty());
        assertThrows(CommonException.class, () -> auctionInfoService.getAuctionInfosDto(ITEM_ID));
    }

    @Test
    void getAuctionInfosDto_PriceInfoNotFound_fail() {
        Item mockItem = mock(Item.class);
        when(mockItem.isAuctionComplete()).thenReturn(false);
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(mockItem));
        when(auctionProgressItemRepo.findPriceByItemItemId(ITEM_ID)).thenReturn(Optional.empty());

        assertThrows(CommonException.class, () -> auctionInfoService.getAuctionInfosDto(ITEM_ID));
    }
}
