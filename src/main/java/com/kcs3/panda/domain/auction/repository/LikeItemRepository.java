package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import com.kcs3.panda.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeItemRepository extends JpaRepository<LikeItem, Long> {

    Optional<LikeItem> findByItem_ItemIdAndUser(Long itemId, User user);

    boolean existsByUserAndItem(User user, Item item);

}
