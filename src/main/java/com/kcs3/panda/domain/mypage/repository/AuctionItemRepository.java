package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionItemRepository {
    //service.getMyLike

    //SELECT l FROM LikeItem l WHERE l.userId = :userId 을 jpql로 자동 생성
    List<LikeItem> findByUserId(Long userId);

    //service.getMyAuction
    //SELET i FROM ITEM i WHERE i.sellerid = :userId
    List<Item> findBySellerId(Long userId);

    @Query("SELECT ap FROM AuctionProgressItem ap WHERE ap.item : = itemId")
    AuctionProgressItem findProgressByItemId(Long itemId);

    @Query("SELECT af FROM AuctionCompleteItem af WHERE af.item  =: itemId")
    AuctionCompleteItem findCompleteByItemid(Long itemId);

    //Service.getMyBid
    @Query("SELECT i FROM AuctionInfo a JOIN a.item i WHERE a.item =: userId AND i.itemId = a.item.itemId")
    List<Item> findItemsByUserId(Long userId);


    @Query ("SELECT ap FROM AuctionProgressItem ap WHERE ap.item =:itemId")
    AuctionProgressItem findItemByItemId(Long itemid);
    //




    @Query("SELECT ac FROM AuctionCompleteItem ac WHERE ac.maxPrice = : userId")
    List<AuctionCompleteItem> findCompleteByUserId(Long userId);
}

