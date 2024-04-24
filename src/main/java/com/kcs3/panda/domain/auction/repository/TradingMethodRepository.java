package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.TradingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingMethodRepository extends JpaRepository<TradingMethod, Long> {
}
