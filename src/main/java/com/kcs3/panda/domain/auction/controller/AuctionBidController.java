package com.kcs3.panda.domain.auction.controller;

import com.kcs3.panda.domain.auction.dto.AuctionBidRequestDto;
import com.kcs3.panda.domain.auction.dto.AuctionInfosDto;
import com.kcs3.panda.domain.auction.service.AuctionBidService;
import com.kcs3.panda.domain.auction.service.AuctionInfoService;
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
    private AuctionInfoService auctionInfoService;
    @Autowired
    private AuctionBidService auctionBidService;

    /**
     * 경매 입찰 내역 표시
     * @return ResponseEntity<ResponseDto<String>> 입찰 결과
     */
    @GetMapping("/{itemId}/bid")
    public ResponseDto<?> getAuctionBids(@PathVariable Long itemId) {
        try {
            AuctionInfosDto auctionInfosDto = auctionInfoService.getAuctionInfosDto(itemId)
                    .orElseThrow(() -> new CommonException(ErrorCode.AUCTION_PRICE_NOT_FOUND));

            return ResponseDto.ok(auctionInfosDto);
        } catch (CommonException e) {
            return ResponseDto.fail(e);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 경매 입찰 참여 API
     * @return ResponseDto<String> 입찰 결과
     */
    @PostMapping("/{itemId}/bid")
    public ResponseDto<?> submitBid(@PathVariable Long itemId,
                                    @RequestBody AuctionBidRequestDto auctionBidRequestDto) {
        try {
            auctionBidService.attemptBid(itemId,
                                        auctionBidRequestDto.userId(),
                                        auctionBidRequestDto.nickname(),
                                        auctionBidRequestDto.bidPrice());
            return ResponseDto.ok("입찰에 성공하였습니다.");
        } catch (CommonException e) {
            return ResponseDto.fail(e);
        } catch (Exception e) {
            log.error("컨트롤러 에러 {}", e.getMessage());
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}