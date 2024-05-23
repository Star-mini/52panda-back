package com.kcs3.panda.domain.auction.service;

import com.kcs3.panda.domain.auction.dto.HotItemListDto;
import com.kcs3.panda.domain.auction.dto.HotItemsDto;
import com.kcs3.panda.domain.auction.entity.*;
import com.kcs3.panda.domain.auction.repository.AuctionInfoRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisServiceMockTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private AuctionInfoRepository auctionInfoRepository;

    @InjectMocks
    private ProgressItemsService progressItemsService;

    @MockBean
    private RedisTemplate<String, HotItemsDto> redisTemplate;

    @Mock
    private ValueOperations<String, HotItemsDto> valueOperations;

    @Test
    @DisplayName("Hot 아이템 목록 Redis 조회 - 검증")
    void testSaveHotItems() {
        // Given
        User user1 = UserFixture.createUser();
        Category category = CategoryFixture.createPhoneCategory();
        TradingMethod tradingMethod = TradingMethodFixture.createBothTradingMethod();
        Region region = RegionFixture.createJungRegion();
        Item progressItem1 = ItemFixture.createProgressItem(user1, category, tradingMethod, region);
        AuctionInfo auctionInfo = AuctionInfoFixture.createProgressAuctionInfo(user1, progressItem1);
        AuctionProgressItem progressItem = AuctionProgressItemFixture.createAuctionProgressItem(progressItem1, user1);

        // Given - Hot item Id 반환
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> hotItemIdList = List.of(progressItem.getItem().getItemId());
        when(auctionInfoRepository.findTop10ItemIds(pageable)).thenReturn(hotItemIdList);

        // Given - Hot AuctionProgressItem으로 엔티티 반환
        List<AuctionProgressItem> hotItemList = new ArrayList<>();
        for (Long itemId : hotItemIdList) {
            when(itemRepository.findByHotItemList(itemId)).thenReturn(progressItem);
            hotItemList.add(progressItem);
        }

        // Given - Mock 객체로 RedisTemplate 설정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), any(HotItemsDto.class));

        // 테스트 코드의 나머지 부분...

        // When -> 실제 service 메서드 호출하여 예측값과 비교
        HotItemListDto hotItemListDto = progressItemsService.getHotItems();
        HotItemsDto hotItemsDto = hotItemListDto.hotItemListDtos().get(0);

        // Then
        assertEquals(progressItem1.getItemId(), hotItemsDto.itemId());
        assertEquals(progressItem.getItemTitle(), hotItemsDto.itemTitle());
        assertEquals(category.getCategory(), hotItemsDto.category());
        assertEquals(progressItem.getThumbnail(), hotItemsDto.thumbnail());
        assertEquals(progressItem.getStartPrice(), hotItemsDto.startPrice());
        assertEquals(progressItem.getBuyNowPrice(), hotItemsDto.buyNowPrice());
    }
}





