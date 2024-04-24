package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionProgressItemRepository extends JpaRepository<AuctionProgressItem, Long> {

}
