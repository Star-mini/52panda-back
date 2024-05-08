package com.kcs3.panda.model;

import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.entity.Region;
import com.kcs3.panda.domain.auction.entity.TradingMethod;

public class ItemFixture {
    public static Item createProgressItem(Category category, TradingMethod tradingMethod, Region region) {
        return Item.builder()
                .category(category)
                .tradingMethod(tradingMethod)
                .region(region)
                .isAuctionComplete(false)
                .build();
    }

    public static Item createCompleteItem(Category category, TradingMethod tradingMethod, Region region) {
        return Item.builder()
                .category(category)
                .tradingMethod(tradingMethod)
                .region(region)
                .isAuctionComplete(true)
                .build();
    }


}
