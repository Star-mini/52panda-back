package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.dto.AuctionBidHighestDto;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionProgressItemRepository extends JpaRepository<AuctionProgressItem, Long> {
    Optional<AuctionBidHighestDto> findAuctionBidHighestByAuctionProgressItemId(Long auctionProgressItemId);
}

