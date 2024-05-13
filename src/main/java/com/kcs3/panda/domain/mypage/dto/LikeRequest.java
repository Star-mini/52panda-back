package com.kcs3.panda.domain.mypage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LikeRequest {
    @NotBlank
    Long likeUserId;

}
