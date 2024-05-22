package com.kcs3.panda.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.kcs3.panda.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional(readOnly = true)
    Optional<User> findByUserNickname(String nickname);  // 닉네임으로 사용자 찾기

    @Transactional(readOnly = true)
    Optional<User> findByUserEmail(String email);  // 이메일로 사용자 찾기

    @Transactional(readOnly = true)
    Optional<User> findByUserId(Long userId);  // 유저 ID로 사용자 찾기
}

