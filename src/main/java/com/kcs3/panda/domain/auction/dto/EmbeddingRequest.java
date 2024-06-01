package com.kcs3.panda.domain.auction.dto;

import lombok.Data;

@Data
public class EmbeddingRequest {
    private double[] embedding;
    private double[] thEmbedding;
}