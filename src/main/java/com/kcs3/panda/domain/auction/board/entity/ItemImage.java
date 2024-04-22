package com.kcs3.panda.domain.auction.board.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemImage {
    private Long id;
    private ItemDetail itemDetail;
    private String imageURL;

}
