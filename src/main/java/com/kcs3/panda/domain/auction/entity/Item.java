package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;



@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "Item")
public class Item extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemId", nullable = false)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "tradingMethodId", nullable = false)
    private TradingMethod tradingMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuctionProgressItemId", nullable = true)
    private AuctionProgressItem auctionProgressItem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuctionCompleteItemId", nullable = true)
    private AuctionCompleteItem auctionCompleteItem;

    @ManyToOne
    @JoinColumn(name = "locationId", nullable = false)
    private Region region;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private boolean isAuctionComplete;

}
