package com.kcs3.panda.model;

import com.kcs3.panda.domain.user.entity.User;

public class   UserFixture {
    public static User createUser() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUserNickname("user1");
        user1.setUserPoint(1000);
        return user1;
    }
}
