package com.kcs3.panda.domain.auction.service;

import com.kcs3.panda.domain.auction.dto.HotItemListDto;
import com.kcs3.panda.domain.auction.dto.HotItemsDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

public class RedisService {




    @Service
    public class ProgressItemsService {

        private final RedisTemplate<String, HotItemsDto> redisTemplate;

        public ProgressItemsService(RedisTemplate<String, HotItemsDto> redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        public void saveHotItemsToRedis(HotItemListDto hotItemListDto) {
            hotItemListDto.hotItemListDtos().forEach(hotItemsDto -> {
                redisTemplate.opsForValue().set("hot_item:" + hotItemsDto.itemId(), hotItemsDto);

            });


        }


    }

}
