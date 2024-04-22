package com.kcs3.panda.domain.auction.board.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuctionProgressItem extends Item {
    private Long id;
    private Item item;
    private String ItemTitle;
    private int starPrice;
    private int buyNowPrice;
    //경매완료시간 넣어야함
    private String location;
    private int maxPrice;
//  최고입찰자아이디 논의 String maxPersonID;


}
