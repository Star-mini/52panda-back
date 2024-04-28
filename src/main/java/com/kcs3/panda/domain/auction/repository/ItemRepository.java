package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  ItemRepository extends JpaRepository<Item, Long> {






    public interface AuctionRepository extends JpaRepository<Item, Long> {
        @Query("SELECT i FROM Item i " +
                "JOIN FETCH i.category c " +
                "JOIN FETCH i.tradingMethod tm " +
                "LEFT JOIN FETCH i.seller " +
                "LEFT JOIN FETCH i.auctionProgressItem " +
                "LEFT JOIN FETCH i.auctionCompleteItem " +
                "WHERE i.isAuctionComplete = :isAuctionComplete")
        List<Item> findItemsWithDetails(@Param("isAuctionComplete") boolean isAuctionComplete);


    }


}
