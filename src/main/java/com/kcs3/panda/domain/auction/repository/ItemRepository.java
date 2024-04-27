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
                "JOIN FETCH i.seller " +
                "JOIN FETCH i.auctionProgressItem ap " +
                "JOIN FETCH i.region r " +
                "WHERE (i.isAuctionComplete = 0) " +
                "AND (c.category = :category) " +
                "AND (tm.tradingMethod = :method) " +
                "AND (r.region = :region)")
        Slice<Item> findByProgressItemsWithLocationAndMethodAndRegion(
                @Param("category") String category,
                @Param("method") Integer method,
                @Param("region") String region,
                Pageable pageable);


        /**
         * 경매완료된 아이템 목록 조회
         */
        @Query("SELECT i FROM Item i " +
                "JOIN FETCH i.category c " +
                "JOIN FETCH i.tradingMethod tm " +
                "JOIN FETCH i.seller " +
                "JOIN FETCH i.auctionCompleteItem aci " +
                "JOIN FETCH i.region r " +
                "WHERE (i.isAuctionComplete = 1) " +
                "AND (c.category = :category) " +
                "AND (tm.tradingMethod = :method) " +
                "AND (r.region = :region)")
        Slice<Item> findByCompleteItemsWithLocationAndMethodAndRegion(
                @Param("category") String category,
                @Param("method") Integer method,
                @Param("region") String region,
                Pageable pageable);







        /**
         *  모든 경매 아이템 목록 조회
         */
        @Query("SELECT i FROM Item i " +
                "JOIN FETCH i.category c " +
                "JOIN FETCH i.tradingMethod tm " +
                "JOIN FETCH i.seller " +
                "JOIN FETCH i.auctionProgressItem ap " +
                "JOIN FETCH i.auctionCompleteItem ac " +
                "JOIN FETCH i.region r " +
                "WHERE (c.category = :category) " +
                "AND (tm.tradingMethod = :method) " +
                "AND (r.region = :region)")
        Slice<Item> findByAllItemsWithLocationAndMethodAndRegion(
                @Param("category") String category,
                @Param("method") Integer method,
                @Param("region") String region,
                Pageable pageable);




}
