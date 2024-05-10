package com.kcs3.panda.domain.auction.service;

import com.kcs3.panda.domain.auction.dto.AuctionBidHighestDto;
import com.kcs3.panda.domain.auction.entity.*;
import com.kcs3.panda.domain.auction.repository.AuctionCompleteItemRepository;
import com.kcs3.panda.domain.auction.repository.AuctionInfoRepository;
import com.kcs3.panda.domain.auction.repository.AuctionProgressItemRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.domain.user.repository.UserRepository;
import com.kcs3.panda.global.exception.CommonException;
import com.kcs3.panda.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuctionBidServiceImplTest {
    @Mock
    private AuctionProgressItemRepository auctionProgressItemRepo;
    @Mock
    private AuctionInfoRepository auctionInfoRepo;
    @Mock
    private AuctionCompleteItemRepository auctionCompleteItemRepo;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuctionBidServiceImpl auctionBidService;

    private final Long itemId = 1L;
    private final Long userId = 1L;
    private final String nickname = "Test user";
    private AuctionProgressItem progressItem;
    private AuctionBidHighestDto highestBidDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Item mockItem = Item.builder()
                .itemId(itemId)
                .build();
        when(itemRepository.getReferenceById(itemId)).thenReturn(mockItem);

        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.setUserNickname(nickname);
        when(userRepository.getReferenceById(userId)).thenReturn(mockUser);

        /*
        // for Builder
        User mockUser = User.builder()
                .userId(userId)
                .userNickname(nickname)
                .build();
        when(userRepository.getReferenceById(userId)).thenReturn(mockUser);
         */

        progressItem = AuctionProgressItem.builder()
                .auctionProgressItemId(itemId)
                .item(mockItem)
                .itemTitle("Test Item")
                .thumbnail("test-thumbnail.jpg")
                .startPrice(5000)
                .buyNowPrice(10000)
                .build();

        when(auctionProgressItemRepo.findByItemItemId(itemId)).thenReturn(Optional.of(progressItem));
    }


    private void setupAuctionBidTest(Long highestBidUserId, int highestBidAmount) {
        highestBidDto = AuctionBidHighestDto.builder()
                .auctionProgressItemId(progressItem.getAuctionProgressItemId())
                .userId(highestBidUserId)
                .maxPersonNickName("Current highest bidder")
                .maxPrice(highestBidAmount)
                .build();
        when(auctionProgressItemRepo.findByItemItemId(itemId)).thenReturn(Optional.of(progressItem));
        when(auctionProgressItemRepo.findHighestBidByAuctionProgressItemId(progressItem.getAuctionProgressItemId())).thenReturn(Optional.of(highestBidDto));
    }

    @Test
    void attemptBid_basic_success() {
        setupAuctionBidTest(2L, 5000);
        int newBidPrice = 6000;

        boolean result = auctionBidService.attemptBid(itemId, userId, nickname, newBidPrice);

        assertTrue(result);
        verify(auctionInfoRepo).save(any(AuctionInfo.class));
        verify(auctionProgressItemRepo).save(any(AuctionProgressItem.class));
        verify(auctionCompleteItemRepo, never()).save(any(AuctionCompleteItem.class));
    }

    @Test
    void attemptBid_buyNowPrice_success() {
        when(auctionProgressItemRepo.findByItemItemId(itemId)).thenReturn(Optional.of(progressItem));
        int bidPrice = 10000;

        boolean result = auctionBidService.attemptBid(itemId, userId, nickname, bidPrice);

        assertTrue(result);
        verify(auctionInfoRepo).save(any(AuctionInfo.class));
        verify(auctionProgressItemRepo).save(any(AuctionProgressItem.class));
        verify(auctionCompleteItemRepo).save(any(AuctionCompleteItem.class));
    }

    @Test
    void attemptBid_bidNotHigher_fail() {
        setupAuctionBidTest(2L, 8000);
        int bidPrice = 5000;

        CommonException exception = assertThrows(CommonException.class, () -> auctionBidService.attemptBid(itemId, userId, nickname, bidPrice));
        assertEquals(ErrorCode.BID_NOT_HIGHER, exception.getErrorCode());
    }

    @Test
    void attemptBid_sameUser_fail() {
        setupAuctionBidTest(1L, 8000);  // 동일 사용자
        int bidPrice = 9000;

        CommonException exception = assertThrows(CommonException.class, () -> auctionBidService.attemptBid(itemId, userId, nickname, bidPrice));
        assertEquals(ErrorCode.BIDDER_IS_SAME, exception.getErrorCode());
    }
}
