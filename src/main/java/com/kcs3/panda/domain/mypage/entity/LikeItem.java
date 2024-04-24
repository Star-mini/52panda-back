package com.kcs3.panda.domain.mypage.entity;

import com.kcs3.panda.domain.auction.board.entity.Item;
import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

public class LikeItem extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY) // 부모인자가 삭제되면 자동 삭제
    private User userid; //User정보 중 id
    @ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY) // 부모인자가 삭제되면 자동 삭제
    private Item itemid;
}
