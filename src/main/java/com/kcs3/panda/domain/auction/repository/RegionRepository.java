package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    // 이름으로 지역 찾기
    Region findByRegion(String region);
}