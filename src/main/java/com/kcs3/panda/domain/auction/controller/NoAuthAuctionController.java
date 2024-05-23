package com.kcs3.panda.domain.auction.controller;

import com.kcs3.panda.domain.auction.dto.ItemDetailRequestDto;
import com.kcs3.panda.domain.auction.dto.NormalResponse;
import com.kcs3.panda.domain.auction.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/no-auth/auction")
public class NoAuthAuctionController {

    private final ItemService itemService;
    private final WebClient webClient = WebClient.create("http://localhost:5000");

    // 물품 상세 목록 가져오기
    @GetMapping("/{itemId}")
    public ResponseEntity<NormalResponse> getItemDetail(@PathVariable Long itemId) {
        ItemDetailRequestDto itemDetail = itemService.getItemDetail(itemId);
        String message = "아이템 상세 정보를 성공적으로 가져왔습니다";
        String status = "success";
        NormalResponse response = new NormalResponse(status, message, itemDetail);
        return ResponseEntity.ok(response);
    }

}
