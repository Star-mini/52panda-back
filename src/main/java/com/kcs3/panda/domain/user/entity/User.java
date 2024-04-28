package com.kcs3.panda.domain.user.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
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
}
