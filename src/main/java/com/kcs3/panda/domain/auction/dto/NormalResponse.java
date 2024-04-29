package com.kcs3.panda.domain.auction.dto;

public class NormalResponse {
    private String status;
    private String message;
    public NormalResponse(String status, String message){
        this.status = status;
        this.message = message;
    }
}
