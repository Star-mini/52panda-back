package com.kcs3.panda.domain.mypage.dto;

import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import com.kcs3.panda.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
public class LikeItemDto extends MypageDto {

    private User userid;
    private LikeItemDto(String itemTitle, int startPrice, int maxPrice, int buyNowPrice) {
        super(itemTitle, startPrice, maxPrice, buyNowPrice);
    }
}
