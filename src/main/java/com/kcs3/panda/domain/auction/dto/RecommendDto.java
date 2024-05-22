package com.kcs3.panda.domain.auction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDto {
    private Long itemId;
    private String itemTitle;
    private String thumbnail;
    private Integer maxPrice;
}
