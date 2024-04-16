package com.kcs3.panda.domain.auction.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class boardsample{
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
