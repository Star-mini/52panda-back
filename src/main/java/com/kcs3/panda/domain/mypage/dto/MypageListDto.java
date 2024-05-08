package com.kcs3.panda.domain.mypage.dto;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import lombok.Builder;

@Builder
public record MypageListDto(
            String itemTitle,
            String category,
            int tradingMethod,
            String thumbnail,
            int startPrice,
            int currentPrice,
            boolean isAuctionComplete
 )
{
    //like & progressitem
    public static MypageListDto fromProgressEntity(Item item,AuctionProgressItem progressItem) {
        if (item == null || item.getCategory() == null || item.getTradingMethod() == null) {
            return null; // Null 체크 추가
        }

        String itemTitle;
        int startPrice;

        return MypageListDto.builder()
                .itemTitle(progressItem.getItemTitle())
                .category(item.getCategory().getCategory())
                .thumbnail(progressItem.getThumbnail())
                .startPrice(progressItem.getStartPrice())
                .currentPrice(progressItem.getMaxPrice() )
                .isAuctionComplete(item.isAuctionComplete())
                .build();
    }
    //like&completeItem
    public static MypageListDto fromCompleteEntity(Item item,AuctionCompleteItem completeItem) {
        if (item == null || item.getCategory() == null || item.getTradingMethod() == null) {
            return null; // Null 체크 추가
        }

        String itemTitle;
        int startPrice;

        return MypageListDto.builder()
                .itemTitle(completeItem.getItemTitle())
                .category(item.getCategory().getCategory())
                .thumbnail(completeItem.getThumbnail())
                .startPrice(completeItem.getStartPrice())
                .currentPrice(completeItem.getMaxPrice() )
                .isAuctionComplete(item.isAuctionComplete())
                .build();
    }



    public static MypageListDto fromEntity(AuctionProgressItem progressItem) {

         if (progressItem == null ) {
               return null; // Null 체크 추가
         }
         Item item = progressItem.getItem(); // merge후 수정


         return MypageListDto.builder()
                   .itemTitle(progressItem.getItemTitle())
                   .category(item.getCategory().getCategory())
                   .tradingMethod(item.getTradingMethod().getTradingMethod())
                   .thumbnail(progressItem.getThumbnail())
                   .startPrice(progressItem.getStartPrice())
                   .currentPrice(progressItem.getMaxPrice())
                   .isAuctionComplete(item.isAuctionComplete())
                   .build();
    }
    public static MypageListDto fromEntity(AuctionCompleteItem completeItem) {
        if (completeItem == null ) {
            return null; // Null 체크 추가
        }
        Item item = completeItem.getItem(); // merge후 수정


        return MypageListDto.builder()
                .itemTitle(completeItem.getItemTitle())
                .category(item.getCategory().getCategory())
                .tradingMethod(item.getTradingMethod().getTradingMethod())
                .thumbnail(completeItem.getThumbnail())
                .startPrice(completeItem.getStartPrice())
                .currentPrice(completeItem.getMaxPrice())
                .isAuctionComplete(item.isAuctionComplete())
                .build();
    }
}
