package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyAuctionProgressRepository extends JpaRepository<AuctionProgressItem,Long> {
    //ItemId -> AuctionProgressItem
    AuctionProgressItem findAuctionProgressItemByItem(Item item);
}
