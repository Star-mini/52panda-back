package com.kcs3.panda.domain.auction.controller;

import com.kcs3.panda.domain.auction.service.AuctionBidService;
import com.kcs3.panda.global.dto.ResponseDto;
import com.kcs3.panda.global.exception.CommonException;
import com.kcs3.panda.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/auction/item")
public class AuctionBidController {

    @Autowired
    private AuctionBidService auctionBidService;

    /**
     * 경매 입찰 참여 API
     * @return ResponseEntity<ResponseDto<String>> 입찰 결과
     */
    @PostMapping("/{itemId}/bid")
    public ResponseEntity<ResponseDto<?>> placeBid(@PathVariable Long itemId,
                                                        @RequestParam int bidPrice,
                                                        @RequestParam Long userId,
                                                        @RequestParam String nickname) {
        try {
            auctionBidService.attemptBid(itemId, userId, nickname, bidPrice);
            return ResponseEntity.ok(ResponseDto.ok("입찰에 성공하였습니다."));
        } catch (CommonException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(ResponseDto.fail(e));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseDto.fail(new CommonException(ErrorCode.INTERNAL_SERVER_ERROR)));
        }
    }
}