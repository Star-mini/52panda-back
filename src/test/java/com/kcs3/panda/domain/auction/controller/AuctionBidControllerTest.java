package com.kcs3.panda.domain.auction.controller;

import com.kcs3.panda.domain.auction.service.AuctionBidService;
import com.kcs3.panda.global.exception.CommonException;
import com.kcs3.panda.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class AuctionBidControllerTest {

    private MockMvc mockMvc;
    private Long itemId = 1L;
    private int bidPrice = 1000;
    private Long userId = 1L;
    private String nickname = "testUser";

    @Mock
    private AuctionBidService auctionBidService;

    @InjectMocks
    private AuctionBidController auctionBidController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(auctionBidController).build();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void submitBid_Success() throws Exception {
        Mockito.when(auctionBidService.attemptBid(anyLong(), anyLong(), anyString(), anyInt()))
                .thenReturn(true);

        mockMvc.perform(post("/api/v1/auth/auction/item/{itemId}/bid", itemId)
                        .param("bidPrice", String.valueOf(bidPrice))
                        .param("userId", String.valueOf(userId))
                        .param("nickname", nickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("입찰에 성공하였습니다."));
    }

    @Test
    public void submitBid_Fail_BidNotHigher() throws Exception {
        Mockito.when(auctionBidService.attemptBid(anyLong(), anyLong(), anyString(), anyInt()))
                .thenThrow(new CommonException(ErrorCode.BID_NOT_HIGHER));

        mockMvc.perform(post("/api/v1/auth/auction/item/{itemId}/bid", itemId)
                        .param("bidPrice", String.valueOf(bidPrice))
                        .param("userId", String.valueOf(userId))
                        .param("nickname", nickname))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(ErrorCode.BID_NOT_HIGHER.getCode()));
    }

    @Test
    public void submitBid_Fail_SameBudder() throws Exception {
        Mockito.when(auctionBidService.attemptBid(anyLong(), anyLong(), anyString(), anyInt()))
                .thenThrow(new CommonException(ErrorCode.BIDDER_IS_SAME));

        mockMvc.perform(post("/api/v1/auth/auction/item/{itemId}/bid", itemId)
                        .param("bidPrice", String.valueOf(bidPrice))
                        .param("userId", String.valueOf(userId))
                        .param("nickname", nickname))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(ErrorCode.BIDDER_IS_SAME.getCode()));
    }
}