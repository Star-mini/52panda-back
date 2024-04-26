package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class QnaComment extends BaseEntity {


    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private ItemQuestion itemQuestion;
    private LocalDateTime commentTime;
    @Column(nullable=false)
    private String comment;
}
