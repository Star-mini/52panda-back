package com.kcs3.panda.domain.auction.board.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
public class Item extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="sellerId")
    private User seller;
    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name="categoryId")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "tradingMethodId")
    private TradingMethod tradingMethod;
    @Column(nullable = false)
    private boolean isAuctionComplete;
}
