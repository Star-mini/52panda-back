package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
