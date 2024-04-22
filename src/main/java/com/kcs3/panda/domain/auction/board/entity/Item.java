package com.kcs3.panda.domain.auction.board.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {
    private Long id;
    private User seller;
    private Category category;
    private TradingMethod tradingMethod;
}
