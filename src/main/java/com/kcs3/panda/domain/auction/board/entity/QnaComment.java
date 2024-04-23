package com.kcs3.panda.domain.auction.board.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Data;


@Entity
@Data
public class QnaComment extends BaseEntity {

    private ItemQuestion itemQuestion;
    private LocalDateTime commentTime;
    @Column(nullable=false)
    private String comment;
}
