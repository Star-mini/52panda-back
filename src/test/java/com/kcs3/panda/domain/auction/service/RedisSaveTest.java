package com.kcs3.panda.domain.auction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kcs3.panda.domain.auction.dto.HotItemsDto;
import com.kcs3.panda.domain.auction.repository.AuctionInfoRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisSaveTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private AuctionInfoRepository auctionInfoRepository;

    @InjectMocks
    private ProgressItemsService progressItemsService;

    @Autowired
    private RedisTemplate<String, HotItemsDto> redisTemplate;

    @Test
    public void saveToRedisTest() {
        // Given
        String key = "hotItems";
        HotItemsDto hotItemsDto = new HotItemsDto(1L,"d","d","d",11,11);

        // When
        redisTemplate.opsForValue().set(key, hotItemsDto);

        // Then
        HotItemsDto savedHotItemsDto = redisTemplate.opsForValue().get(key);
        assertEquals(hotItemsDto, savedHotItemsDto, "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
    }
}
