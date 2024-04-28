package com.kcs3.panda.domain.mypage.dto;

import com.kcs3.panda.domain.user.entity.User;

public class PostAuctionItemDto extends MypageDto{
    private User user;
    private int itemId;
    private PostAuctionItemDto(String itemTitle, int startPrice, int maxPrice, int buyNowPrice) {
        super(itemTitle, startPrice, maxPrice, buyNowPrice);
    }
}
