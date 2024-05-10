package com.kcs3.panda.domain.auction.dto;

public record AuctionBidRequestDto(Long itemId,
                                   int bidPrice,
                                   Long userId,
                                   String nickname
) {
}
