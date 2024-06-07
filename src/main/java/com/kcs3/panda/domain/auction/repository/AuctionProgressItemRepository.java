package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.dto.AuctionBidHighestDto;
import com.kcs3.panda.domain.auction.dto.AuctionPriceDto;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface AuctionProgressItemRepository extends JpaRepository<AuctionProgressItem, Long> {
    Optional<AuctionProgressItem> findByItemItemId(Long itemId);

    @Query("SELECT new com.kcs3.panda.domain.auction.dto.AuctionPriceDto(api.buyNowPrice, api.maxPrice) " +
            "FROM AuctionProgressItem api " +
            "WHERE api.item.itemId = :itemId")
    Optional<AuctionPriceDto> findPriceByItemItemId(Long itemId);

    @Query("SELECT new com.kcs3.panda.domain.auction.dto.AuctionBidHighestDto(" +
                "api.auctionProgressItemId, user.userId, user.userNickname, api.maxPrice) " +
            "FROM AuctionProgressItem api " +
            "JOIN api.user user " +
            "WHERE api.auctionProgressItemId = :auctionProgressItemId")
    Optional<AuctionBidHighestDto> findHighestBidByAuctionProgressItemId(@Param("auctionProgressItemId") Long auctionProgressItemId);

    @Query("SELECT api FROM AuctionProgressItem api JOIN FETCH api.item WHERE api.bidFinishTime < :now")
    Optional<List<AuctionProgressItem>> findAllByBidFinishTimeBefore(LocalDateTime now);
}

