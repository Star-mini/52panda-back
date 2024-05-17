package com.kcs3.panda.domain.auction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuctionBidRequestDto(Long itemId,
                                   @JsonProperty("price") int bidPrice,
                                   Long userId,
                                   String nickname
) {
}
