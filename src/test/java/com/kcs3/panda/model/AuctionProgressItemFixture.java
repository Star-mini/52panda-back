package com.kcs3.panda.model;

import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.user.entity.User;

import java.time.LocalDateTime;

public class AuctionProgressItemFixture {
    public static AuctionProgressItem createAuctionProgressItem(Item item, User user) {
        return AuctionProgressItem.builder()
                .auctionProgressItemId(1L)
                .item(item)
                .ItemTitle("진행중인 경매물품1")
                .thumbnail("thumbnail_image_url")
                .startPrice(0)
                .buyNowPrice(1000)
                .bidFinishTime(LocalDateTime.now().plusDays(14))
                .location("jung")
                .user(user)
                .maxPersonNickName("user1")
                .maxPrice(500)
                .build();
    }
}
