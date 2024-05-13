package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "QnaComment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class QnaComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="qnaCommentId", nullable = false)
    private Long qnaCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId", nullable = false)
    private ItemQuestion questionId;

    @Column(nullable = false)
    private String comment;
}
