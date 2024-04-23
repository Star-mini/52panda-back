package com.kcs3.panda.domain.auction.board.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
public class ItemQuestion extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;
    @Column(nullable = false)
    private String questionUserId;
    private LocalDateTime questionTime;
    @Column(nullable = false)
    private String questionContents;
}
