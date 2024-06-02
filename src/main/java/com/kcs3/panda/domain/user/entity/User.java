package com.kcs3.panda.domain.user.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String userNickname;

    @Column(nullable = false, length = 255, unique = true)  // 이메일 필드 추가
    private String userEmail;  // 이메일

    @Column(nullable = false, columnDefinition = "int default 0")
    private int userPoint;

    @ElementCollection  // 리스트 매핑
    @CollectionTable(name = "user_cookies", joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "cookie")
    private List<Long> cookies = new ArrayList<>();  // 유저가 클릭한 물품 ID 리스트
}
