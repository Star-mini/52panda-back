package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Item extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="sellerId")
    private User seller;
    @ManyToOne
    @JoinColumn(name="categoryId" ,nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "tradingMethodId",nullable = false)
    private TradingMethod tradingMethod;
    @Column(nullable = false)
    private boolean isAuctionComplete;
}
