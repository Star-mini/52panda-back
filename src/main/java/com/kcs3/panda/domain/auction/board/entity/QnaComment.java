package com.kcs3.panda.domain.auction.board.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QnaComment {
    private long id;
    private ItemQuestion itemQuestion;
//    댓글등록시간
    private String comment;
}
