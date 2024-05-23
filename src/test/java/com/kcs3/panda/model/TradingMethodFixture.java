package com.kcs3.panda.model;


import com.kcs3.panda.domain.auction.entity.TradingMethod;

public class TradingMethodFixture {
    public static TradingMethod createBothTradingMethod() {
        return TradingMethod.builder()
                .tradingMethodId(1L)
                .tradingMethod(2)
                .build();
    }
}
