package com.kcs3.panda.domain.mypage.entity;

import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Entity
@Getter
@Setter
public class LikeItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long likeid; //찜 id
    @JoinColumn(name ="userId")
    @Column(nullable = false)
    @ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY) // 부모인자가 삭제되면 자동 삭제
    private User userid; //User정보 중 id
    @ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY) // 부모인자가 삭제되면 자동 삭제
    @Column(nullable = false)
    private Item itemid;
}
