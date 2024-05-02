package com.kcs3.panda.domain.auction.repository;
import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface AuctionInfoRepository extends JpaRepository<AuctionInfo, Long> {


    /**
     * Hot ItemId 10개 조회
     * 네이티브 쿼리 쓴 이유! 메서드이름쿼리를 사용하려고 했지만, 그룹화 정렬화 불가능!
     * 그래서 JPQL로 구성했고, 네이티브 쿼리를 사용하여 LIMIT 10 설정함
     */



    @Query("SELECT DISTINCT item.itemId " +
            "FROM AuctionInfo ai " +
            "JOIN ai.item item " +
            "WHERE item.isAuctionComplete = false " +
            "GROUP BY item.itemId " +
            "ORDER BY COUNT(ai.user) DESC")
    List<Long> findTop10ItemIds(Pageable pageable);


//    @Query("SELECT DISTINCT item.itemId " +
//            "FROM AuctionInfo ai " +
//            "JOIN ai.item item " +
//            "WHERE item.isAuctionComplete = false " +
//            "GROUP BY item.itemId " +
//            "ORDER BY COUNT(ai.user) DESC")
//    List<Long> findTop10ItemIds();









}
