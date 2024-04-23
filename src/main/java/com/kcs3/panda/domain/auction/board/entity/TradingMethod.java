package com.kcs3.panda.domain.auction.board.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;


@Entity
@Data
public class TradingMethod extends BaseEntity {
    @Column(nullable = false)
    private int traingMethod;
}
