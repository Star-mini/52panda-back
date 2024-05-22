package com.kcs3.panda.domain.auction.controller;

import com.kcs3.panda.domain.auction.dto.EmbeddingRequest;
import com.kcs3.panda.domain.auction.dto.ItemDetailRequestDto;
import com.kcs3.panda.domain.auction.dto.NormalResponse;
import com.kcs3.panda.domain.auction.dto.RecommendDto;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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

    // 리액트에서 파이썬으로 임베딩 정보 전달
    @PostMapping("/Recommendation/Embedding")
    public ResponseEntity<NormalResponse> postRecommendation(@RequestBody EmbeddingRequest embeddingRequest) {
        try {
            Mono<ResponseEntity<String>> response = webClient.post()
                    .uri("/api/embedding")
                    .bodyValue(embeddingRequest)
                    .retrieve()
                    .toEntity(String.class);

            ResponseEntity<String> result = response.block();
            if (result != null && result.getStatusCode().is2xxSuccessful()) {
                String message = "임베딩 추천 정보를 성공적으로 전달했습니다.";
                return ResponseEntity.ok(new NormalResponse("success", message));
            } else {
                String message = "임베딩 추천 정보 전달에 실패했습니다.";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new NormalResponse("fail", message));
            }
        } catch (Exception e) {
            String message = "임베딩 추천 정보 전달 중 오류가 발생했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new NormalResponse("fail", message));
        }
    }

    //플라스크에서 받은 아이템list  dto로 작성
    @PostMapping("/Recommendation/Embedding/makeDto")
    public ResponseEntity<NormalResponse> makeDtoFromEmbedding(@RequestBody List<Long> itemIds) {
        try {
            List<RecommendDto> itemDetails = itemService.getItemsByIds(itemIds);
            String message = "DTO 생성을 성공적으로 완료했습니다.";
            return ResponseEntity.ok(new NormalResponse("success", message, itemDetails));
        } catch (Exception e) {
            String message = "DTO 생성 중 오류가 발생했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new NormalResponse("fail", message));
        }
    }
}
