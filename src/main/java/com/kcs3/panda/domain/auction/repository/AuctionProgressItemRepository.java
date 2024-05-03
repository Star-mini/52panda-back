package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.dto.AuctionBidHighestDto;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface AuctionProgressItemRepository extends JpaRepository<AuctionProgressItem, Long> {
    Optional<AuctionProgressItem> findByItemId(Long itemId);
    Optional<AuctionBidHighestDto> findAuctionBidHighestByAuctionProgressItemId(Long auctionProgressItemId);

    Optional<List<AuctionProgressItem>> findAllByBidFinishTimeBefore(LocalDateTime now);
}

