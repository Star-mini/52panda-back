package com.kcs3.panda.domain.chat.repository;

import com.kcs3.panda.domain.chat.entity.ChattingContent;
import com.kcs3.panda.domain.chat.entity.ChattingRoom;
import com.kcs3.panda.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom,Long> {
    List<ChattingRoom> findByBuyerOrSeller(User buyer, User seller);
}
