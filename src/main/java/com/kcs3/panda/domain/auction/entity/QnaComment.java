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
@Table(name = "QnaComment")
public class QnaComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="qnaCommentId", nullable = false)
    private Long qnaCommentId;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private ItemQuestion itemQuestion;
    private LocalDateTime commentTime;
    @Column(nullable=false)
    private String comment;
}
