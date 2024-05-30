package com.kcs3.panda.domain.auction.repository;

import com.kcs3.panda.domain.auction.entity.Alarm;
import com.kcs3.panda.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findTop4ByUserOrderByCreatedAtDesc(User user);

}
