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
    public static MypageListDto fromEntity(Item item) {
        if (item == null || item.getCategory() == null || item.getTradingMethod() == null) {
            return null; // Null 체크 추가
        }

        String itemTitle;
        int startPrice;
        if (item.getAuctionProgressItem() != null) {
            itemTitle = item.getAuctionProgressItem().getItemTitle();
            startPrice = item.getAuctionProgressItem().getStartPrice();
        } else if (item.getAuctionCompleteItem() != null) {
            itemTitle = item.getAuctionCompleteItem().getItemTitle();
            startPrice = item.getAuctionCompleteItem().getStartPrice();
        } else {
            // 어떤 경우에도 progressItem이나 completeItem이 없으면 처리할 수 없음
            return null;
        }

        return MypageListDto.builder()
                .itemTitle(itemTitle)
                .category(item.getCategory().getCategory())
                .thumbnail(item.getAuctionProgressItem().getThumbnail())
                .startPrice(startPrice)
                .currentPrice(item.getAuctionProgressItem() != null ? item.getAuctionProgressItem().getMaxPrice() : 0)
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
                   .thumbnail(item.getAuctionProgressItem().getThumbnail())
                   .startPrice(item.getAuctionProgressItem().getStartPrice())
                   .currentPrice(item.getAuctionProgressItem().getMaxPrice())
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
                .thumbnail(item.getAuctionCompleteItem().getThumbnail())
                .startPrice(item.getAuctionProgressItem().getStartPrice())
                .currentPrice(item.getAuctionProgressItem().getMaxPrice())
                .isAuctionComplete(item.isAuctionComplete())
                .build();
    }
}
