package com.kcs3.panda.domain.auction.board.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemDetail {
    private long id;
    private Item item;
    //글등록시간
    private String itemDetailContent;
}
