package com.kcs3.panda.model;


import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.auction.entity.Region;

public class RegionFixture {
    public static Region createJungRegion() {
        return Region.builder()
                .regionId(1L)
                .region("jung")
                .build();
    }


}
