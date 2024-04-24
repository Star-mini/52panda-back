package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.ItemQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemQuestionRepository extends JpaRepository<ItemQuestion, Long> {
}
