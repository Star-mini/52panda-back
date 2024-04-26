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
@Table(name = "AuctionInfo")
public class AuctionInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="auctionInfoId", nullable = false)
    private Long auctionInfoId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="itemId")
    private Item item;
    @Column(nullable = false)
    private int bidPrice;
}
