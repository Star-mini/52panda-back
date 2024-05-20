package com.kcs3.panda.domain.auction.dto;

import lombok.Builder;

@Builder
public record AuctionPriceDto(int buyNowPrice,
                              int maxPrice
) {
}
