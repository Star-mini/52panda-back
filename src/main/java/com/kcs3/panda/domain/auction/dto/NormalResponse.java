package com.kcs3.panda.domain.auction.dto;

public class NormalResponse {
    private String status;
    private String message;

    private Object data; // 추가된 필드


    public NormalResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }


    public NormalResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}