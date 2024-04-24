package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ItemQuestion extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "itemId",nullable = false)
    private Item item;
    @Column(nullable = false)
    private String questionUserId;
    @Column(nullable = false)
    private LocalDateTime questionTime;
    @Column(nullable = false)
    private String questionContents;
}
