package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MyCompleteItemRepository extends JpaRepository<AuctionCompleteItem,Long> {
    List<AuctionCompleteItem> findByUser(User user);
}
