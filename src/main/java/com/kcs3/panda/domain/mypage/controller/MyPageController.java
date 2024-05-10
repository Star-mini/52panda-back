package com.kcs3.panda.domain.mypage.controller;

import com.kcs3.panda.domain.mypage.dto.MypageListDto;
import com.kcs3.panda.domain.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/mypage")
public class MyPageController {
    @Autowired
    private MypageService mypageService;

    //User Id 추출

    @GetMapping
    public Long getUserIdFromToken() {
        // 현재 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 아이디 추출
        Long userId = Long.valueOf(authentication.getName());

        // 사용자 아이디를 이용하여 마이페이지 정보 조회
       return userId;
    }



    //좋아요 페이지
    @RequestMapping("/like")
    public List<MypageListDto> getMyLike(){
        Long userId = getUserIdFromToken();


        return mypageService.getLikedItemByUserId(userId);
    }

    //경매 등록 페이지
    @RequestMapping("/auction")
    public List<MypageListDto> getMyAuction(){

       Long userId = getUserIdFromToken();

        return mypageService.getMyAuctionByUserId(userId);

    }

    //입찰 참여 페이지
    @RequestMapping("/bid")
    public List<MypageListDto> getMyBid(){
        Long userId = getUserIdFromToken();

        return mypageService.getMyBidByUserId(userId);

    }

    //입찰 완료 페이지
    @RequestMapping("/award")
    public List<MypageListDto> getMyAward(){
        Long userId = getUserIdFromToken();

        return mypageService.getMyCompleteByUserId(userId);

    }



}

