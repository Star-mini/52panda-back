/*
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

        return ResponseEntity.ok.build();
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
 */
//수정 후
package com.kcs3.panda.domain.mypage.controller;

import com.kcs3.panda.domain.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/mypage")
public class MyPageController {
    @Autowired
    private MypageService mypageService;

    //User Id 추출
    @GetMapping
    public String myPageId() {
        // 현재 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 아이디 추출
        String userId = authentication.getName();

        // 사용자 아이디를 이용하여 마이페이지 정보 조회
       return userId;
    }

    //좋아요 페이지
    @RequestMapping("/like")
    public void getMyLike(){

    }

    //경매 등록 페이지
    @RequestMapping("/auction")
    public void getMyAuction(){

    }
    //입찰 참여 페이지
    @RequestMapping("/bid")
    public void getMyBid(){

    }
    //입찰 완료 페이지
    @RequestMapping("/award")
    public void getMyAward(){

    }



}

