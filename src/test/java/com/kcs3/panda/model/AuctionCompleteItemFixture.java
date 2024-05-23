package com.kcs3.panda.model;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.user.entity.User;

import java.time.LocalDateTime;

public class AuctionCompleteItemFixture {
    public static AuctionCompleteItem  createAuctionCompleteItem(Item item, User user) {
        return AuctionCompleteItem.builder()
                .auctionCompleteItemId(1L)
                .item(item)
                .ItemTitle("완료된 경매물품1")
                .thumbnail("thumbnail_image_url")
                .startPrice(100)
                .buyNowPrice(1000)
                .bidFinishTime(LocalDateTime.now().plusDays(14))
                .location("jung")
                .user(user)
                .maxPersonNickName("경매당첨자2")
                .maxPrice(500)
                .build();
    }


}
