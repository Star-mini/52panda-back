package com.kcs3.panda.domain.chat.repository;

import com.kcs3.panda.domain.chat.entity.ChattingContent;
import com.kcs3.panda.domain.chat.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom,Long> {

}
