package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//public interface AuctionProgressItemRepository extends JpaRepository<AuctionProgressItem, Long> {
//    @Query("SELECT i.auctionProgressItem FROM Item i WHERE i.itemId=:itemId")
//    Optional<AuctionProgressItem> findByitemId(@Param("itemId") Long itemId);
//
//}
