package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.dto.AuctionInfoSummeryDto;
import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionInfoRepository extends JpaRepository<AuctionInfo, Long> {
    @Query("SELECT new com.kcs3.panda.domain.auction.dto.AuctionInfoSummeryDto(user.userNickname, ai.bidPrice) " +
            "FROM AuctionInfo ai " +
            "JOIN ai.user user " +
            "WHERE ai.item.itemId = :itemId")
    List<AuctionInfoSummeryDto> findInfoSummariesByItemId(Long itemId);
}
