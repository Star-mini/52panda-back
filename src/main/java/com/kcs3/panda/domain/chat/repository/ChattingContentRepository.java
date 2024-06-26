package com.kcs3.panda.domain.chat.repository;

import com.kcs3.panda.domain.chat.entity.ChattingContent;
import com.kcs3.panda.domain.chat.entity.ChattingRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingContentRepository extends JpaRepository<ChattingContent,Long> {
    List<ChattingContent> findByChattingRoom(ChattingRoom chattingRoom);

    ChattingContent findTopByChattingRoomOrderByCreatedAtDesc(ChattingRoom chattingRoom);
}
