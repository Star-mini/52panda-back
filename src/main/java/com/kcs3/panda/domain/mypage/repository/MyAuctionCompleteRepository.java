package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyAuctionCompleteRepository extends JpaRepository<AuctionCompleteItem,Long> {
    //ItemId -> AuctionCompleteItem
    //@Query("SELECT af FROM AuctionCompleteItem af WHERE af.item  =: itemId")
    AuctionCompleteItem findCompleteItemByItem(Item item);

}
