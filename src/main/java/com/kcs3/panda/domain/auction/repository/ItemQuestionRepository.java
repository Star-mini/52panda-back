package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.ItemDetail;
import com.kcs3.panda.domain.auction.entity.ItemQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemQuestionRepository extends JpaRepository<ItemQuestion, Long> {
    List<ItemQuestion> findByItemDetailId_ItemDetailId(Long itemDetailId);
}
