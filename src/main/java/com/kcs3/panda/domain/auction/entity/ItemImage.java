package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ItemImage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "itemDetailId")
    private ItemDetail itemDetail;
    @Column(nullable = false)
    private String imageURL;

}
