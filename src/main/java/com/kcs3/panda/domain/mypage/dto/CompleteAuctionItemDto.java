package com.kcs3.panda.domain.mypage.dto;

import com.kcs3.panda.domain.user.entity.User;

public class CompleteAuctionItemDto extends MypageDto{
    private User userId;
    private User sellerId;

    private CompleteAuctionItemDto(String itemTitle, int startPrice, int maxPrice, int buyNowPrice) {
        super(itemTitle, startPrice, maxPrice, buyNowPrice);
    }
}
