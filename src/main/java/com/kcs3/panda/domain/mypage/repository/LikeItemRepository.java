/*
package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.mypage.entity.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MypageRepository extends JpaRepository<LikeItem, Long> {
    //@Query("SELECT a FROM LikeItem a WHERE a.userid ) 쿼리문 잘못 짠듯,,,
    List<LikeItem> findLikeItemsBy(@Param("userid") Long userid );

}
*/
//수정 1차
package com.kcs3.panda.domain.mypage.repository;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface LikeItemRepository {

    List<LikeItem> findByItemId(Long itemId);
    //SELECT l FROM LikeItem l WHERE l.item.id = :itemId 을 jpql로 자동 생성
    List<LikeItem> findByUserId(Long userId);
}

