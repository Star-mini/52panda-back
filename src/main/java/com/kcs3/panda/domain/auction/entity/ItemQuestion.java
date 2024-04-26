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
@Table(name = "ItemQuestion")
public class ItemQuestion extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemQuestionId", nullable = false)
    private Long itemQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId",nullable = false)
    private ItemDetail itemDetail;
    @Column(nullable = false)
    private String questionUserId;
    @Column(nullable = false)
    private LocalDateTime questionTime;
    @Column(nullable = false)
    private String questionContents;
}
