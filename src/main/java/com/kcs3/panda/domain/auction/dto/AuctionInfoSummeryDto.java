package com.kcs3.panda.domain.auction.dto;

import lombok.Builder;

@Builder
public record AuctionInfoSummeryDto(String name,
                                        int price
) {
}
