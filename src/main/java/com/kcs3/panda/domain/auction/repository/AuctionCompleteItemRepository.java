package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.dto.AuctionPriceDto;
import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuctionCompleteItemRepository extends JpaRepository<AuctionCompleteItem, Long> {
    @Query("SELECT new com.kcs3.panda.domain.auction.dto.AuctionPriceDto(aci.buyNowPrice, aci.maxPrice) " +
            "FROM AuctionCompleteItem aci " +
            "WHERE aci.item.itemId = :itemId")
    Optional<AuctionPriceDto> findPriceByItemItemId(Long itemId);

    Optional<AuctionCompleteItem> findByItemItemId(Long itemId);
}
