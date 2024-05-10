package com.kcs3.panda.model;

import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.entity.Region;
import com.kcs3.panda.domain.auction.entity.TradingMethod;
import com.kcs3.panda.domain.user.entity.User;

public class ItemFixture {
    public static Item createProgressItem(User user, Category category, TradingMethod tradingMethod, Region region) {
        return Item.builder()
                .itemId(1L)
                .seller(user)
                .category(category)
                .tradingMethod(tradingMethod)
                .region(region)
                .isAuctionComplete(false)
                .build();
    }

    public static Item createCompleteItem(User user, Category category, TradingMethod tradingMethod, Region region) {
        return Item.builder()
                .itemId(2L)
                .seller(user)
                .category(category)
                .tradingMethod(tradingMethod)
                .region(region)
                .isAuctionComplete(true)
                .build();
    }


}
