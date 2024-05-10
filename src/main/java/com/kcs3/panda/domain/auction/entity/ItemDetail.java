package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "ItemDetail")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate

public class ItemDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemDetailId", nullable = false)
    private Long itemDetailId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId",nullable = false)
    private Item item;

    private LocalDateTime itmeCreateTime;

    @Column(nullable = false)
    private String itemDetailContent;
}
