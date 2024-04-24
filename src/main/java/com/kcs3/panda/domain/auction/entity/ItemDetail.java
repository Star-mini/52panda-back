package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ItemDetail extends BaseEntity {
    @OneToOne
    private Item item;
    private LocalDateTime itmeCreateTime;
    @Column(nullable = false)
    private String itemDetailContent;
}
