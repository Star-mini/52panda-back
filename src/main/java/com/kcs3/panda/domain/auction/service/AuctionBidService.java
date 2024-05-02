package com.kcs3.panda.domain.auction.service;

public interface AuctionBidService {
    /**
     * 입찰 시도
     */
    boolean attemptBid(Long itemId, Long userId, String nickname, int bidPrice) throws Exception;
}
