package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@DynamicUpdate
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemId", nullable = false)
    private Long itemId;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE) // 찜 삭제 설정
    private List<LikeItem> likeItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId")
    private User seller;


    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "tradingMethodId", nullable = false)
    private TradingMethod tradingMethod;

    @ManyToOne
    @JoinColumn(name = "regionId", nullable = false)
    private Region region;

    @Column(nullable = false)
    private boolean isAuctionComplete;
}
