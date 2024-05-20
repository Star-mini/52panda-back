package com.kcs3.panda.model;

import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.entity.Region;
import com.kcs3.panda.domain.auction.entity.TradingMethod;
import com.kcs3.panda.domain.user.entity.User;

public class CategoryFixture {

    public static Category
    createPhoneCategory() {
        return Category.builder()
                .categoryId(1L)
                .category("phone")
                .build();

    }
}
