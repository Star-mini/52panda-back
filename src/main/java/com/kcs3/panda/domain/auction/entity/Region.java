package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "region")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="regionId", nullable = false)
    private Long regionId;

    @Column(nullable = false)
    private String region;
}
