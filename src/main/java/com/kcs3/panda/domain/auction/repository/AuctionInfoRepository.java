package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionInfoRepository extends JpaRepository<AuctionInfo, Long> {
}
