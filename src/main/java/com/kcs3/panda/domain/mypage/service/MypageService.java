package com.kcs3.panda.domain.mypage.service;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.mypage.dto.MypageListDto;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import com.kcs3.panda.domain.mypage.repository.*;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.domain.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class MypageService {
    @Autowired
    private MypageLikeRepository mypageLikeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyAuctionProgressRepository myAuctionProgressRepository;
    @Autowired
    private MyAuctionCompleteRepository myAuctionCompleteRepository;
    @Autowired
    private MyAuctionSellRepository myAuctionSellRepository;
    @Autowired
    private MyAuctionlistRepository myAuctionlistRepository;
    @Autowired
    private MyCompleteItemRepository myCompleteItemRepository;

    public List<MypageListDto> getLikedItemByUserId(Long userId, Pageable pageable) {
        User user = new User();
        user = userRepository.findByUserId(userId);
        Slice<LikeItem> likedItems = mypageLikeRepository.findByUser(user,pageable);


        List<MypageListDto> likedItem = new ArrayList<>();

        for (LikeItem likeItem : likedItems) {
            Item item = likeItem.getItem();
            if (item != null & item.isAuctionComplete() == false) {
                //ProgressItem
                AuctionProgressItem progressItem = myAuctionProgressRepository.findAuctionProgressItemByItem(item);
                likedItem.add(MypageListDto.fromProgressEntity(item, progressItem));

            } else if (item != null & item.isAuctionComplete() == true) {
                //CompleteItem
                AuctionCompleteItem completeItem = myAuctionCompleteRepository.findCompleteItemByItem(item);
                likedItem.add(MypageListDto.fromCompleteEntity(item, completeItem));

            }


        }

        return likedItem;
    }


    //경매 등록한 아이템 조회
    public List<MypageListDto> getMyAuctionByUserId(Long userId, Pageable pageable) {
        User user = new User();
        user = userRepository.findByUserId(userId);
        //사용자가 등록한 아이템 조회
        Slice<Item> userItems = myAuctionSellRepository.findBySeller(user, pageable);

        //입찰 &낙찰 아이템 조회
        List<MypageListDto> auctionItems = new ArrayList<>();

        for (Item item : userItems) {
            if (item != null & item.isAuctionComplete() == false) {
                AuctionProgressItem progressItem = myAuctionProgressRepository.findAuctionProgressItemByItem(item);
                auctionItems.add(MypageListDto.fromProgressEntity(item, progressItem));
            } else if (item != null & item.isAuctionComplete() == true) {
                AuctionCompleteItem completeItem = myAuctionCompleteRepository.findCompleteItemByItem(item);
                auctionItems.add(MypageListDto.fromCompleteEntity(item, completeItem));
            }
        }

        return auctionItems;
    }


//입찰 참여 조회 (현재 입찰중인 건만 조회)
    public List<MypageListDto> getMyBidByUserId(Long userId,Pageable pageable){

        User user = new User();
        user = userRepository.findByUserId(userId);

        Slice<Item> Item = myAuctionlistRepository.findByUser(user,pageable);

        List<MypageListDto> getMyBids = new ArrayList<>();
        for(Item item: Item){
            AuctionProgressItem progressItem = myAuctionProgressRepository.findAuctionProgressItemByItem(item);

            if(progressItem !=null)
                getMyBids.add(MypageListDto.fromEntity(progressItem));

        }
        //현재 입찰 진행중인것만 조회 하도록 기능 설정
        return getMyBids;
    }


//낙찰 참여 조회
    public List<MypageListDto> getMyCompleteByUserId(Long userId,Pageable pageable) {
        User user = new User();
        user = userRepository.findByUserId(userId);

        Slice<AuctionCompleteItem> completeItem = myCompleteItemRepository.findByUser(user,pageable);
        List<MypageListDto> completeItems = new ArrayList<>();

        for(AuctionCompleteItem item : completeItem){
            completeItems.add(MypageListDto.fromEntity(item));
        }
        return completeItems;
    }



}




