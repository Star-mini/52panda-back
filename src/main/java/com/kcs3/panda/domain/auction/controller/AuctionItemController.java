package com.kcs3.panda.domain.auction.controller;

import com.kcs3.panda.domain.auction.dto.AuctionItemRequest;
import com.kcs3.panda.domain.auction.dto.NormalResponse;
import com.kcs3.panda.domain.auction.dto.QnaPostRequest;
import com.kcs3.panda.domain.auction.dto.CommentRequest;
import com.kcs3.panda.domain.auction.service.ItemService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/auction")
public class AuctionItemController
{
    @Autowired
    private final ItemService itemService;
    @PostMapping("/{itemid}/qna/")
    public ResponseEntity<NormalResponse> postQna(@RequestBody QnaPostRequest request, @PathVariable("itemid") long id){
        itemService.postQna(request,id);
        String message = "문의글 등록을 성공하였습니다";
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

    @DeleteMapping("/{itemid}/qna/{questionid}/")
    public ResponseEntity<NormalResponse> deleteQna(@PathVariable("questionid") long questionid)
    {
        itemService.deleteQna(questionid);
        String message = "문의글 삭제를 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status,message));
    }
    @PostMapping("/form/")
    public ResponseEntity<NormalResponse> postAuctionItem(@RequestBody AuctionItemRequest request) throws IOException {
        itemService.postItem(request);
        String message = "물품 등록을 성공하였습니다";
        String status = "success";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NormalResponse(status,message));
    }
}
