package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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
@Table(name = "AuctionProgressItem")
public class AuctionProgressItem extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="auctionProgressItemId", nullable = false)
    private Long auctionProgressItemId;


    @Column(nullable = false)
    private String ItemTitle;
    @Column(nullable = false)
    private int starPrice;
    private int buyNowPrice;
    private LocalDateTime bidFinishTime;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private int maxPrice;
    private String maxPersonID;


}