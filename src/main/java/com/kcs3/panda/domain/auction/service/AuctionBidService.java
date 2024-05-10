package com.kcs3.panda.domain.auction.service;

import com.kcs3.panda.domain.auction.dto.AuctionInfosDto;

import java.util.Optional;

public interface AuctionBidService {
    /**
     * 입찰 시도
     */
    boolean attemptBid(Long itemId, Long userId, String nickname, int bidPrice) throws Exception;

    /**
     * 시간에 따른 경매 완료
     */
    void finishAuctionsByTime();
}
