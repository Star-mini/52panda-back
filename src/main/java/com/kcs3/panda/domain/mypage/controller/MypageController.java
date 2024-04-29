package com.kcs3.panda.domain.mypage.controller;

import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("") //mypage api 경로
public class MypageController {
    private final MypageService mypageService;

    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }
    @RequestMapping("") //Like Item
    public ResponseEntity<List<Item>> getUserLikes(@PathVariable Long userId){

        return ResponseEntity.ok().build();
    }
    @RequestMapping("") //등록아이템 조회
    public ResponseEntity<List<Item>> getPostItem(@PathVariable Long uerId){
        return null;
    }

    @RequestMapping("") //입찰 참여 조회
    public ResponseEntity<List<Item>> getJoinAuctionItem(@PathVariable Long uerId){
        return null;
    }
    @RequestMapping("") //낙찰 아이템 조회
    public ResponseEntity<List<Item>> getCompleteAuctionItem(@PathVariable Long uerId){
        return null;
    }
}
