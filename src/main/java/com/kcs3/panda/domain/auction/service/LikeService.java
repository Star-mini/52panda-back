package com.kcs3.panda.domain.auction.service;

import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import com.kcs3.panda.domain.auction.repository.LikeItemRepository;
import com.kcs3.panda.domain.mypage.entity.LikeItem;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.domain.user.repository.UserRepository;
import java.util.Optional;

import com.kcs3.panda.global.config.oauth.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Service
@RequiredArgsConstructor
public class LikeService {
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final LikeItemRepository likeItemRepository;
    @Autowired
    private final UserRepository userRepository;


    public boolean postLike(Long itemId, Long userId) {
        User user = userRepository.findByUserId(userId).get();
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Invalid item ID"));

        // 중복 체크
        if (likeItemRepository.existsByUserAndItem(user, item)) {
            return false; // 이미 찜 목록에 있음
        }

        LikeItem likeItem = new LikeItem();
        likeItem.setItem(item);
        likeItem.setUser(user);
        likeItemRepository.save(likeItem);
        return true; // 찜 목록에 새로 추가됨
    }


    public boolean deleteLike(Long itemId) {
        // SecurityContextHolder에서 인증된 유저 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // 인증된 유저의 ID를 가져오기
        Long userId = customOAuth2User.getUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Optional<LikeItem> OlikeItem = likeItemRepository.findByItem_ItemIdAndUser(itemId, user);

        if (OlikeItem.isPresent()) {
            LikeItem likeItem = OlikeItem.get();
            likeItemRepository.delete(likeItem);
            return true;
        } else {
            return false;
        }
    }
}
