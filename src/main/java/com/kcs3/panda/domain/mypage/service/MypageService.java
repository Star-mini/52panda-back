package com.kcs3.panda.domain.mypage.service;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.mypage.dto.MypageListDto;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import com.kcs3.panda.domain.mypage.repository.AuctionItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MypageService {
    private AuctionItemRepository auctionItemRepository;

    public List<MypageListDto> getLikedItemByUserId(Long userId) {

        List<LikeItem> likedItems = auctionItemRepository.findByUserId(userId);

        List<MypageListDto> likedItem = new ArrayList<>();
        // DTO 설정이 안되어있어서 error 터짐!
        for(LikeItem likeItem: likedItems){
            Item item = likeItem.getItem();
            if(item!=null&item.isAuctionComplete()==false){
               //ProgressItem
                AuctionProgressItem progressItem = auctionItemRepository.findProgressByItemId(item.getItemId());
                likedItem.add(MypageListDto.fromProgressEntity(item,progressItem));
            }
            else if(item!=null&item.isAuctionComplete()==true){
                //CompleteItem
                AuctionCompleteItem completeItem = auctionItemRepository.findCompleteByItemid(item.getItemId());
                likedItem.add(MypageListDto.fromCompleteEntity(item,completeItem));
            }

        }

        return likedItem;
    }

//경매 아이템 조회
    public List<MypageListDto> getMyAuctionByUserId(Long userId) {
        //사용자가 등록한 아이템 조회
        List<Item> userItems = auctionItemRepository.findBySellerId(userId);

        //입찰 &낙찰 아이템 조회
        List<MypageListDto> auctionItems = new ArrayList<>();

        for (Item item : userItems) {
            if(item!=null&item.isAuctionComplete()==false) {
                AuctionProgressItem progressItem = auctionItemRepository.findProgressByItemId(item.getItemId());
                auctionItems.add(MypageListDto.fromProgressEntity(item,progressItem));
            }
            else if(item !=null&item.isAuctionComplete()==true){
                AuctionCompleteItem completeItem = auctionItemRepository.findCompleteByItemid(item.getItemId());
                auctionItems.add(MypageListDto.fromCompleteEntity(item,completeItem));
            }
        }

        return auctionItems;
    }
//입찰 참여 조회 (현재 입찰중인 건만 조회)
    public List<MypageListDto> getMyBidByUserId(Long userId){
        List<Item> items = auctionItemRepository.findItemsByUserId(userId);
        List<MypageListDto> getMyBids = new ArrayList<>();
        for(Item item: items){
            AuctionProgressItem progressItem = auctionItemRepository.findItemByItemId(item.getItemId());

            if(progressItem !=null)
                getMyBids.add(MypageListDto.fromEntity(progressItem));

        }
        //현재 입찰 진행중인것만 조회 하도록 기능 설정
        return getMyBids;
    }


//낙찰 참여 조회
    public List<MypageListDto> getMyCompleteByUserId(Long userId) {
        List<AuctionCompleteItem> completeItem = auctionItemRepository.findCompleteByUserId(userId);
        List<MypageListDto> completeItems = new ArrayList<>();

        for(AuctionCompleteItem item : completeItem){
            completeItems.add(MypageListDto.fromEntity(item));
        }
        return completeItems;
    }

}


