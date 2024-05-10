package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import com.kcs3.panda.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MypageLikeRepository extends JpaRepository<LikeItem,Long> {

    List<LikeItem> findByUser(User user);

}