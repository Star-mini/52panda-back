package com.kcs3.panda.domain.auction.dto;

import lombok.Builder;

import java.util.List;


@Builder
public record ProgressItemListDto(
        List<ProgressItemsDto> progressItemListDto

) {}
