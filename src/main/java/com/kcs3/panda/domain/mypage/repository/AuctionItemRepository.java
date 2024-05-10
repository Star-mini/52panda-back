/*package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Repository
public interface AuctionItemRepository extends JpaRepository<Item,Long> {
    //service.getMyLike
    //userId -> likeItem
    //SELECT l FROM LikeItem l WHERE l.userId = :userId 을 jpql로 자동 생성
    List<LikeItem> findByUserId(Long userId);

    //service.getMyAuction
    //SELET i FROM ITEM i WHERE i.sellerid = :userId
    //userId -> Item
    List<Item> findBySellerId(Long userId);

    //ItemId -> AuctionProgressItem
    @Query("SELECT ap FROM AuctionProgressItem ap WHERE ap.item =: itemId")
    AuctionProgressItem findProgressByItemId(Long itemId);
    //ItemId -> AuctionCompleteItem

    @Query("SELECT af FROM AuctionCompleteItem af WHERE af.item  =: itemId")
    AuctionCompleteItem findCompleteByItemid(Long itemId);

    //Service.getMyBid
    //User Id -> Item
    @Query("SELECT i FROM AuctionInfo a JOIN a.item i WHERE a.item =: userId AND i.itemId = a.item.itemId")
    List<Item> findItemsByUserId(Long userId);


    @Query ("SELECT ap FROM AuctionProgressItem ap WHERE ap.item =:itemId")
    AuctionProgressItem findItemByItemId(Long itemid);
    //iemId -> AuctionProgressItem




    @Query("SELECT ac FROM AuctionCompleteItem ac WHERE ac.maxPrice = : userId")
    List<AuctionCompleteItem> findCompleteByUserId(Long userId);

    @Query("SELECT ap FROM AuctionProgressItem ap where ap.item.itemId =:itemId")
    Optional<AuctionProgressItem> findProgressItemByItemId(Long itemId);

    @Query("SELECT ac FROM AuctionCompleteItem ac where ac.item.itemId =:itemId")
    Optional<AuctionCompleteItem> findCompleteItemByItemId(Long itemId);
}
*/
