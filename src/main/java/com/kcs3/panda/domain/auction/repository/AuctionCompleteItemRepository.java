package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionCompleteItemRepository extends JpaRepository<AuctionCompleteItem, Long> {
    @Query("SELECT i.auctionCompleteItem FROM Item i WHERE i.itemId=:itemId")
    Optional<AuctionCompleteItem> findByitemId(@Param("itemId")Long itemId);


}
