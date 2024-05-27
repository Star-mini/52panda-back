package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import com.kcs3.panda.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MypageLikeRepository extends JpaRepository<LikeItem,Long> {

    Slice<LikeItem> findByUser(User user, Pageable pageable);

    Optional<LikeItem> findByUserAndItem(User user, Item item);
}
