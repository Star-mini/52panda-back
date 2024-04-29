package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {
}
