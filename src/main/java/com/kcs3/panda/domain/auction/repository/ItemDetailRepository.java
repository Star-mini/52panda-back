package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {
}
