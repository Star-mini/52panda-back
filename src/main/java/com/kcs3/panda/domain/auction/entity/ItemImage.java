package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
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
