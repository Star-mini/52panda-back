/*
package com.kcs3.panda.domain.mypage.repository;

import com.kcs3.panda.domain.mypage.entity.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MypageRepository extends JpaRepository<LikeItem, Long> {
    //@Query("SELECT a FROM LikeItem a WHERE a.userid ) 쿼리문 잘못 짠듯,,,
    List<LikeItem> findLikeItemsBy(@Param("userid") Long userid );

}
*/
//수정 1차
package com.kcs3.panda.domain.mypage.repository;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


