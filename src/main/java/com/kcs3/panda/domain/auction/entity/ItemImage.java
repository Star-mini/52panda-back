package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "ItemImage")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class ItemImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemImageId", nullable = false)
    private Long itemImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemDetailId")
    private ItemDetail itemDetail;

    @Column(nullable = false)
    private String imageURL;
}
