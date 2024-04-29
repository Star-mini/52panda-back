package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT i.category FROM Item i WHERE i.itemId=:itemId")
    Optional<Category> findByItemId(@Param("itemId") Long itemId);

}
