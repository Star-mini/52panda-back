package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface MyAuctionlistRepository extends JpaRepository<AuctionInfo,Long> {
    @Query("SELECT DISTINCT item " +
            "FROM AuctionInfo ai " +
            "JOIN ai.item item " +
            "WHERE ai.user = :user " +
            "GROUP BY item.itemId ")
    List<Item> findByUser(@Param("user") User user);
}
