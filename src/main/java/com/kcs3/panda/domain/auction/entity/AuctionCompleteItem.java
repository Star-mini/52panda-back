package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
public class AuctionCompleteItem extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "itemId", nullable = true)
    private Item item;
    @Column(nullable = false)
    private String ItemTitle;
    @Column(nullable=false)
    private int starPrice;
    private int buyNowPrice;
    private LocalDateTime bidFinishTime;
    @Column(nullable=false)
    private String location;
    @Column(nullable=false)
    private int maxPrice;
    private String maxPersonID;
}
