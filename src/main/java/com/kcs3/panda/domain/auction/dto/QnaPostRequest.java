package com.kcs3.panda.domain.auction.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QnaPostRequest {
    @NotBlank
    Long questionUserId;
    @NotBlank
    String questionContents;
}
