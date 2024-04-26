package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemId", nullable = false)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId")
    private User seller;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuctionProgressItemId", nullable = true)
    private AuctionProgressItem auctionProgressItem;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuctionCompleteItemId", nullable = true)
    private AuctionCompleteItem auctionCompleteItem;
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
    private TradingMethod tradingMethod;
    @Column(nullable = false)
    private boolean isAuctionComplete;

}
