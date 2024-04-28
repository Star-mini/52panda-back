package com.kcs3.panda.domain.mypage.dto;



public class JoinAuctionItemDto extends MypageDto{
    private Long auctionItemId;
    private int bidPrice;
    public JoinAuctionItemDto(String itemTitle, int startPrice, int maxPrice, int buyNowPrice) {
        super(itemTitle, startPrice, maxPrice, buyNowPrice);
    }
}
