package com.kcs3.panda.domain.chat.repository;

import com.kcs3.panda.domain.chat.entity.ChattingContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingContentRepository extends JpaRepository<ChattingContent,Long> {
}
