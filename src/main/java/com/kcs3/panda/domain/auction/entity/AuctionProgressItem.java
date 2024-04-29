package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AuctionProgressItem extends BaseEntity {


    @Column(nullable = false)
    private String ItemTitle;
    @Column(nullable = false)
    private int startPrice;
    private int buyNowPrice;
    private LocalDateTime bidFinishTime;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private int maxPrice;
    @Column(nullable=false)
    private String maxPersonID;


}
