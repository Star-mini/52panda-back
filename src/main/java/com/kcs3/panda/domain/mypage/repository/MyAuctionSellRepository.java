package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MyAuctionSellRepository extends JpaRepository<Item,Long> {
    //
    Slice<Item> findBySeller(User user, Pageable pageable);
}
