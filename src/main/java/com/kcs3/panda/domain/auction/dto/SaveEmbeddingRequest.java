package com.kcs3.panda.domain.auction.dto;


import lombok.Data;
@Data
public class SaveEmbeddingRequest {
    private double[] embedding;
    private double[] thEmbedding;
    private double[] categoryEmbedding;
    private double[] detailEmbedding;
}
