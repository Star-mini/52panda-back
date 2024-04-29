package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface  ItemRepository extends JpaRepository<Item, Long> {



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
                "WHERE (:category IS NULL OR c.category = :category) " +
                "AND (:method IS NULL OR tm.tradingMethod = :method) " +
                "AND (:region IS NULL OR r.region = :region)")
        Slice<Item> findByAllItemWithLocationAndMethodAndRegion(
                @Param("category") String category,
                @Param("method") Integer method,
                @Param("region") String region,
                Pageable pageable);


        /**
         *  진행 경매 아이템 목록 조회
         */
        @Query("SELECT i FROM Item i " +
                "JOIN FETCH i.category c " +
                "JOIN FETCH i.tradingMethod tm " +
                "JOIN FETCH i.seller " +
                "JOIN FETCH i.auctionProgressItem ap " +
                "JOIN FETCH i.region r " +
                "WHERE (:category IS NULL OR c.category = :category) " +
                "AND (:method IS NULL OR tm.tradingMethod = :method) " +
                "AND (:region IS NULL OR r.region = :region)")
        Slice<Item> findByProgressItemWithLocationAndMethodAndRegion(
                @Param("category") String category,
                @Param("method") Integer method,
                @Param("region") String region,
                Pageable pageable);

        /**
         *  완료된 경매 아이템 목록 조회
         */
        @Query("SELECT i FROM Item i " +
                "JOIN FETCH i.category c " +
                "JOIN FETCH i.tradingMethod tm " +
                "JOIN FETCH i.seller " +
                "JOIN FETCH i.auctionCompleteItem ac " +
                "JOIN FETCH i.region r " +
                "WHERE (:category IS NULL OR c.category = :category) " +
                "AND (:method IS NULL OR tm.tradingMethod = :method) " +
                "AND (:region IS NULL OR r.region = :region)")
        Slice<Item> findByCompleteItemWithLocationAndMethodAndRegion(
                @Param("category") String category,
                @Param("method") Integer method,
                @Param("region") String region,
                Pageable pageable);


}
