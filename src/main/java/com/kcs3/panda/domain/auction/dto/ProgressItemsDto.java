package com.kcs3.panda.domain.auction.dto;


import com.kcs3.panda.domain.auction.entity.Item;
import lombok.Builder;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

@Builder
public record ProgressItemsDto(
        String itemTitle,
        String category,
        int tradingMethod,
        String thumbnail,
        int startPrice,
        int currentPrice,
        boolean isAuctionComplete
) {
    public static ProgressItemsDto fromEntity(Item item) {

        if (item == null || item.getAuctionProgressItem() == null || item.getCategory() == null || item.getTradingMethod() == null) {
            return null; // Null 체크 추가
        }


        return ProgressItemsDto.builder()
                .itemTitle(item.getAuctionProgressItem().getItemTitle())
                .category(item.getCategory().getCategory())
                .tradingMethod(item.getTradingMethod().getTraingMethod())
                .thumbnail(item.getThumbnail())
                .startPrice(item.getAuctionProgressItem().getStarPrice())
                .currentPrice(item.getAuctionProgressItem().getMaxPrice())
                .isAuctionComplete(item.isAuctionComplete())
                .build();
    }

}
