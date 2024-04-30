
package com.kcs3.panda.domain.mypage.entity;

import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.model.BaseEntity;
import com.kcs3.panda.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Data
public class LikeItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long likeId; //찜 id
    @ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY) // 부모인자가 삭제되면 자동 삭제
    @JoinColumn(name ="userId")
    private User user;

    @ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY) // 부모인자가 삭제되면 자동 삭제
    @JoinColumn (name="itemId")
    private Item item;

}