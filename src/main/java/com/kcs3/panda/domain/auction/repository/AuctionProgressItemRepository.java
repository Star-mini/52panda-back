package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionProgressItemRepository extends JpaRepository<AuctionProgressItem, Long> {
    @Query("SELECT a FROM AuctionProgressItem a JOIN Item item ON a.auctionCompleteId = "
            + "item.auctionCompleteId ")
    AuctionProgressItem findByitemId(Long itemId);

}
