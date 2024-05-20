package com.kcs3.panda.domain.mypage.controller;

import com.kcs3.panda.domain.mypage.dto.MypageListDto;
import com.kcs3.panda.domain.mypage.service.MypageService;
import com.kcs3.panda.global.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
/*
    @GetMapping
    public Long getUserIdFromToken() {
        // 현재 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 아이디 추출
        Long userId = Long.valueOf(authentication.getName());

        // 사용자 아이디를 이용하여 마이페이지 정보 조회
       return userId;
    }
*/


    //좋아요 페이지
    @RequestMapping("/like")
    public List<MypageListDto> getMyLike(@PageableDefault(size =10)Pageable pageable){
        //Long userId = getUserIdFromToken();
        Long userId = 2L;

        return mypageService.getLikedItemByUserId(userId,pageable);
    }

    //경매 등록 페이지
    @RequestMapping("/auction")
    public List<MypageListDto> getMyAuction(@PageableDefault(size =10)Pageable pageable){

       //Long userId = getUserIdFromToken();
        Long userId = 1L;
        return mypageService.getMyAuctionByUserId(userId,pageable);

    }

    //입찰 참여 페이지
    @RequestMapping("/bid")
    public List<MypageListDto> getMyBid(@PageableDefault(size =10)Pageable pageable){
        //Long userId = getUserIdFromToken();
        Long userId = 1L;
        return mypageService.getMyBidByUserId(userId,pageable);

    }

    //입찰 완료 페이지
    @RequestMapping("/award")
    public List<MypageListDto> getMyAward(@PageableDefault(size =10)Pageable pageable){
        //Long userId = getUserIdFromToken();
        Long userId = 1L;
        return mypageService.getMyCompleteByUserId(userId,pageable);

    }



}

