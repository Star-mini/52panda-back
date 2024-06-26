// Recommend 엔티티 수정
package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Recommend")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Recommend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommendId", nullable = false)
    private Long recommendId;

    @Column(columnDefinition = "TEXT")
    private String embedding;

    @Column(columnDefinition = "TEXT")
    private String thEmbedding;

    @Column(columnDefinition = "TEXT")
    private String categoryEmbedding;

    @Column(columnDefinition = "TEXT")
    private String detailEmbedding;

    @Column(columnDefinition = "TEXT")
    private String representEmbedding;

    @Column(name = "item_id", nullable = false)
    private Long itemId;
}
