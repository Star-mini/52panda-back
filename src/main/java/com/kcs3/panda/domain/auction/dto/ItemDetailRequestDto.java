// ItemDetailRequestDto.java
package com.kcs3.panda.domain.auction.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemDetailRequestDto {
    private Long itemId;
    private String title;
    private String categoryName;
    private LocalDateTime bidFinishTime;
    private int startPrice;
    private int maxPrice;
    private Integer buyNowPrice;
    private LocalDateTime itemCreateTime;
    private String itemDetailContent;
    private boolean auctionComplete;

    private Long sellerId;
    private String userNickname;

    private List<ImageDTO> images;

    private List<QuestionDTO> questions;

    @Data
    public static class ImageDTO {
        private String imageURL;
    }

    @Data
    public static class QuestionDTO {
        private Long questionId;
        private String questionContents;
        private LocalDateTime questionTime;
        private List<CommentDTO> comments;

        @Data
        public static class CommentDTO {
            private Long commentId;
            private LocalDateTime commentTime;
            private String comment;
        }
    }
}

