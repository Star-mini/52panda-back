package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    Recommend findByItemId(Long itemId);
}
