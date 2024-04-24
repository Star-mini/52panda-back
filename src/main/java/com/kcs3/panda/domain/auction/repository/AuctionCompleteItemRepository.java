package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionCompleteItemRepository extends JpaRepository<AuctionCompleteItem, Long> {
}
