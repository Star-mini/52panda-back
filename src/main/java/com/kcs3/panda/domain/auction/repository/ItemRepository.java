package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;



public interface  ItemRepository extends JpaRepository<Item, Long> {



        /**
         * 경매진행중인 아이템 목록 조회
         */
        @Query("SELECT i FROM Item i " +
                "JOIN FETCH i.category c " +
                "JOIN FETCH i.tradingMethod tm " +
                "INNER JOIN FETCH i.seller " +
                "INNER JOIN FETCH i.auctionProgressItem ap " +
                "JOIN FETCH i.region r " +
                "WHERE i.isAuctionComplete = 0 " +
                "AND (:category IS NULL OR c.category = :category) " +
                "AND (:method IS NULL OR tm.tradingMethod = :method) " +
                "AND (:region IS NULL OR r.region = :region)")
        Slice<Item> findByProgressItemsWithLocationAndmethodAndRegion(
                @Param("category") String category,
                @Param("method") Integer method,
                @Param("region") String region,
                Pageable pageable);





}
