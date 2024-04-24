package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
