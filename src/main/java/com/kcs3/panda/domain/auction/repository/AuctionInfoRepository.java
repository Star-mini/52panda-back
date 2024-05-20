package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.dto.AuctionInfoSummeryDto;
import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import org.springframework.data.domain.Pageable;
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


    /**
     * Hot ItemId 10개 조회
     */

    @Query("SELECT DISTINCT item.itemId " +
            "FROM AuctionInfo ai " +
            "JOIN ai.item item " +
            "WHERE item.isAuctionComplete = false " +
            "GROUP BY item.itemId " +
            "ORDER BY COUNT(ai.user) DESC")
    List<Long> findTop10ItemIds(Pageable pageable);



    /**
     * New ItemId 10개 조회
     */
    @Query("SELECT DISTINCT item.itemId " +
            "FROM Item item " + // 공백 추가
            "WHERE item.isAuctionComplete = false " +
            "ORDER BY item.itemId DESC")
    List<Long> findNew10ItemIds(Pageable pageable);






}
