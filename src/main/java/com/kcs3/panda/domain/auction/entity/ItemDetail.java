package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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
@Table(name = "ItemDetail")
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
