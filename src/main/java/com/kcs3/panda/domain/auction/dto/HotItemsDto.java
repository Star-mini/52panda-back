package com.kcs3.panda.domain.auction.dto;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import lombok.Builder;


@Builder
public record HotItemsDto(
        Long itemId,
        String itemTitle,
        String category,
        String thumbnail,
        int startPrice

) {

    public static HotItemsDto fromHotEntity(AuctionProgressItem auctionProgressItem) {
        return HotItemsDto.builder()
                .itemId(auctionProgressItem.getItem().getItemId())
                .itemTitle(auctionProgressItem.getItemTitle())
                .category(auctionProgressItem.getItem().getCategory().getCategory())
                .thumbnail(auctionProgressItem.getThumbnail())
                .startPrice(auctionProgressItem.getStartPrice())
                .build();
    }
}
