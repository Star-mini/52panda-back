package com.kcs3.panda.domain.auction.dto;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;

public record CompletionItemsDto(
        String itemTitle,
        String category,
        int tradingMethod,
        String thumbnail,
        int startPrice,
        int currentPrice,
        boolean isAuctionComplete

) {

    public static ProgressItemsDto fromEntity(AuctionCompleteItem completeItem) {
        return ProgressItemsDto.builder()
                .itemTitle(completeItem.getItemTitle())
                .category(completeItem.getItem().getCategory().getCategory())
                .tradingMethod(completeItem.getItem().getTradingMethod().getTradingMethod())
                .thumbnail(completeItem.getThumbnail())
                .startPrice(completeItem.getStartPrice())
                .currentPrice(completeItem.getMaxPrice())
                .isAuctionComplete(completeItem.getItem().isAuctionComplete())
                .build();
    }
}
