package com.kcs3.panda.domain.auction.board.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemQuestion {
    private Long id;
    private Item item;
    private String questionUserId;
    //문의등록시간
    private String questionContents;
}
