package com.kcs3.panda.model;

import com.kcs3.panda.domain.auction.entity.*;
import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.*;

public class AuctionInfoFixture {

    public static AuctionInfo createProgressAuctionInfo(User user, Item item) {
        return AuctionInfo.builder()
                .auctionInfoId(1L)
                .user(user)
                .item(item)
                .bidPrice(500)
                .build();
    }




}
