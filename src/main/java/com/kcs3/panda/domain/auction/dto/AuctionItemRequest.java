package com.kcs3.panda.domain.auction.dto;

import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.auction.entity.TradingMethod;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class AuctionItemRequest {
    @NotBlank
    public String title;
    public List<MultipartFile> images;
    @NotBlank
    public Category category;
    @NotBlank
    public TradingMethod trading_method;
    @NotBlank
    public int start_price;
    public Integer  buy_now_price;
    @NotBlank
    public String contents;

    @NotBlank
    public LocalDateTime finish_time;
}
