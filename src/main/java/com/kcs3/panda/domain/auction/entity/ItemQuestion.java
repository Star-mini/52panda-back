package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ItemQuestion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class ItemQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemQuestionId", nullable = false)
    private Long itemQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemDetailId", nullable = false)
    private ItemDetail itemDetailId;

    @Column(nullable = false)
    private Long questionUserId;

    @Column(nullable = false)
    private String questionContents;

    // 연관된 QnaComment들을 관리하기 위한 리스트
    @OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QnaComment> comments = new ArrayList<>();
}
