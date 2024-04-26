package com.kcs3.panda.domain.mypage.dto;

import com.kcs3.panda.domain.user.entity.User;

public abstract class MypageDto {
    private String itemTitle;
    private int startPrice;
    private int maxPrice;
    private int buyNowPrice;

    public MypageDto(String itemTitle,int startPrice,int maxPrice, int buyNowPrice){
        this.itemTitle= itemTitle;
        this.startPrice=startPrice;
        this.maxPrice= maxPrice;
        this.buyNowPrice=buyNowPrice;

    }
}
