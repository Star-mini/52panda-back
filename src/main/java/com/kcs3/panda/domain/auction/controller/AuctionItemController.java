package com.kcs3.panda.domain.auction.controller;

import com.kcs3.panda.domain.auction.dto.AuctionItemRequest;
import com.kcs3.panda.domain.auction.dto.NormalResponse;
import com.kcs3.panda.domain.auction.dto.QnaPostRequest;
import com.kcs3.panda.domain.auction.dto.CommentRequest;
import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.auction.entity.TradingMethod;
import com.kcs3.panda.domain.auction.service.ItemService;
import com.kcs3.panda.domain.auction.service.LikeService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
public class AuctionItemController
{
    @Autowired
    private final ItemService itemService;
    @Autowired
    private final LikeService likeService;


    @PostMapping("/{itemid}/qna/")
    public ResponseEntity<NormalResponse> postQna(@RequestBody QnaPostRequest request, @PathVariable("itemid") long id){
        itemService.postQna(request,id);
        String message = "문의글 등록을 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status,message));
    }

    @DeleteMapping("/{itemid}/qna/{questionid}/")
    public ResponseEntity<NormalResponse> deleteQna(@PathVariable("questionid") long questionid)
    {
        itemService.deleteQna(questionid);
        String message = "문의글 삭제를 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status,message));
    }




    @PostMapping("/{itemid}/qna/{questionid}/")
    public ResponseEntity<NormalResponse> postComment(@RequestBody CommentRequest request, @PathVariable("questionid") long id){
        itemService.postComment(request,id);
        String message = "문의댓글 등록을 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status,message));
    }


    @DeleteMapping("/{itemid}/comment/{CommentId}")
    public ResponseEntity<NormalResponse> deleteComment(@PathVariable("CommentId") long id)
    {
        itemService.deleteComment(id);
        String message = "문의댓글을 삭제하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status,message));
    }

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


    @PostMapping("/{itemid}/like/")
    public ResponseEntity<NormalResponse> postItemLike(@PathVariable("itemid") long itemid){
        likeService.postLike(itemid);
        String message = "찜목록에 등록을 성공하였습니다.";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status,message));

    }
    @DeleteMapping("/{itemid}/like/")
    public ResponseEntity<NormalResponse> deleteItemLike(@PathVariable("itemid") long itemid){
        likeService.deleteLike(itemid);
        String message = "찜목록 삭제를 성공하였습니다.";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status,message));

    }
}
