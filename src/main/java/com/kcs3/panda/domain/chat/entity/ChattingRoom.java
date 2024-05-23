package com.kcs3.panda.domain.chat.entity;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChattingRoom extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chattingRoomId", nullable = false)
    private Long chattingRoomId;

    @ManyToOne
    @JoinColumn(name = "buyerId")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    private User seller;

    @OneToOne
    @JoinColumn(name = "auctionCompleteId")
    private AuctionCompleteItem auctionCompleteItem;

    @Builder
    public ChattingRoom(User buyer, User seller) {
        this.buyer = buyer;
        this.seller = seller;
    }
}
