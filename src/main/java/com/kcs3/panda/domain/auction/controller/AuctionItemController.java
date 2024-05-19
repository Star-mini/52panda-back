package com.kcs3.panda.domain.auction.controller;

import com.kcs3.panda.domain.auction.dto.*;
import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.auction.entity.TradingMethod;
import com.kcs3.panda.domain.auction.service.ItemService;
import com.kcs3.panda.domain.auction.service.LikeService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.kcs3.panda.domain.mypage.dto.LikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/auction")
public class AuctionItemController {
    @Autowired
    private final ItemService itemService;
    @Autowired
    private final LikeService likeService;

    //문의글 등록
    @PostMapping("/{itemid}/qna/")
    public ResponseEntity<NormalResponse> postQna(@RequestBody QnaPostRequest request, @PathVariable("itemid") long id) {
        itemService.postQna(request, id);
        String message = "문의글 등록을 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status, message));
    }

    //문의글 삭제
    @DeleteMapping("/{itemid}/qna/{questionid}/")
    public ResponseEntity<NormalResponse> deleteQna(@PathVariable("questionid") long questionid) {
        itemService.deleteQna(questionid);
        String message = "문의글 삭제를 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status, message));
    }


    //문의댓글 등록
    @PostMapping("/{itemid}/qna/{questionid}/")
    public ResponseEntity<NormalResponse> postComment(@RequestBody CommentRequest request, @PathVariable("questionid") long id) {
        itemService.postComment(request, id);
        String message = "문의댓글 등록을 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status, message));
    }

    //문의댓글 삭제
    @DeleteMapping("/{itemid}/comment/{CommentId}")
    public ResponseEntity<NormalResponse> deleteComment(@PathVariable("CommentId") long id) {
        itemService.deleteComment(id);
        String message = "문의댓글을 삭제하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status, message));
    }

    //물품등록
    @PostMapping(value = "/form/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NormalResponse> postAuctionItem(
            @RequestParam("title") String title,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("category") Category category,
            @RequestParam("trading_method") TradingMethod tradingMethod,
            @RequestParam("start_price") int startPrice,
            @RequestParam("buy_now_price") int buyNowPrice,
            @RequestParam("contents") String contents,
            @RequestParam("finish_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finishTime
    ) throws IOException {

        AuctionItemRequest request = new AuctionItemRequest();
        request.title = title;
        request.images = images;
        request.category = category;
        request.trading_method = tradingMethod;
        request.start_price = startPrice;
        request.buy_now_price = buyNowPrice;
        request.contents = contents;
        request.finish_time = finishTime;

        itemService.postItem(request);

        String message = "물품 등록을 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status, message));
    }
    
    //임베딩값저장 컨트롤러
    @PostMapping("/{itemId}/embedding")
    public ResponseEntity<Void> updateEmbedding(
            @PathVariable Long itemId,
            @RequestBody double[] embedding) {
        itemService.updateEmbedding(itemId, embedding);
        return ResponseEntity.ok().build();
    }

    //찜목록에 등록
    @PostMapping("/{itemid}/like/")
    public ResponseEntity<NormalResponse> postItemLike(@PathVariable("itemid") long itemid, @RequestBody LikeRequest likeRequest) {
        boolean isLiked = likeService.postLike(itemid, likeRequest.getLikeUserId());
        String message;
        HttpStatus status;

        if (isLiked) {
            message = "찜목록에 등록을 성공하였습니다.";
            status = HttpStatus.CREATED;
        } else {
            message = "이미 찜목록에 등록된 아이템입니다.";
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(new NormalResponse("success", message));
    }

    //찜목록 삭제
    @DeleteMapping("/{itemid}/like/")
    public ResponseEntity<NormalResponse> deleteItemLike(@PathVariable("itemid") long itemid) {
        boolean isDeleted = likeService.deleteLike(itemid);
        String message = isDeleted ? "찜목록 삭제를 성공하였습니다." : "찜목록 삭제를 실패하였습니다.";
        String status = isDeleted ? "success" : "fail";
        return ResponseEntity.status(HttpStatus.OK).body(new NormalResponse(status, message));
    }

    //물품상세목록 가져오기
    @GetMapping("/{itemId}")
    public ResponseEntity<NormalResponse> getItemDetail(@PathVariable Long itemId) {
        ItemDetailRequestDto itemDetail = itemService.getItemDetail(itemId);
        String message = "아이템 상세 정보를 성공적으로 가져왔습니다";
        String status = "success";
        NormalResponse response = new NormalResponse(status, message, itemDetail);
        return ResponseEntity.ok(response);
    }
}
