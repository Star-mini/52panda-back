package com.kcs3.panda.domain.auction.service;

import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.repository.AuctionCompleteItemRepository;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.domain.auction.repository.AuctionInfoRepository;
import com.kcs3.panda.domain.auction.repository.AuctionProgressItemRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import com.kcs3.panda.domain.user.repository.UserRepository;
import com.kcs3.panda.domain.auction.dto.AuctionBidHighestDto;
import com.kcs3.panda.global.exception.CommonException;
import com.kcs3.panda.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuctionBidServiceImpl implements AuctionBidService {
    @Autowired
    private AuctionProgressItemRepository auctionProgressItemRepo;
    @Autowired
    private AuctionCompleteItemRepository auctionCompleteItemRepo;
    @Autowired
    private AuctionInfoRepository auctionInfoRepo;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean attemptBid(Long itemId, Long userId, String nickname, int bidPrice) {
        AuctionProgressItem progressItem = auctionProgressItemRepo.findByItemItemId(itemId)
                .orElseThrow(() -> new CommonException(ErrorCode.ITEM_NOT_FOUND));

        if (bidPrice >= progressItem.getBuyNowPrice()) {
            log.debug("User {}가 Item {}을 즉시 구매 - 가격: {}", itemId, userId, bidPrice);

            saveAuctionInfo(itemId, userId, bidPrice);
            updateAuctionProgressItemMaxBid(progressItem, userId, nickname, bidPrice);
            transferItemToComplete(progressItem);
            return true;
        }

        Optional<AuctionBidHighestDto> highestBid
                = auctionProgressItemRepo.findAuctionBidHighestByAuctionProgressItemId(progressItem.getAuctionProgressItemId());

        highestBid.ifPresent(bid -> {
            if (bidPrice <= bid.maxPrice()) {
                throw new CommonException(ErrorCode.BID_NOT_HIGHER);
            }
            if (userId.equals(bid.userId())) {
                throw new CommonException(ErrorCode.BIDDER_IS_SAME);
            }
        });

        saveAuctionInfo(itemId, userId, bidPrice);
        updateAuctionProgressItemMaxBid(progressItem, userId, nickname, bidPrice);
        return true;
    }//end attemptBid()

    private void saveAuctionInfo(Long itemId, Long userId, int price) {
        Item item = itemRepository.getReferenceById(itemId);    //프록시 객체 참조
        User user = userRepository.getReferenceById(userId);

        try {
            item.getItemId();
            user.getUserId();
        } catch (EntityNotFoundException e) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        AuctionInfo auctionInfo = AuctionInfo.builder()
                .item(item)
                .user(user)
                .bidPrice(price)
                .build();
        auctionInfoRepo.save(auctionInfo);
    }//end saveAuctionInfo()

    private void updateAuctionProgressItemMaxBid(AuctionProgressItem progressItem, Long userId, String nickname, int bidPrice) {
        User user = userRepository.getReferenceById(userId);
        progressItem.updateAuctionMaxBid(user, nickname, bidPrice);
        auctionProgressItemRepo.save(progressItem);
    }//end updateAuctionProgressItemMaxBid()


    @Transactional
    @Scheduled(cron = "0 0 * * * *")  // 매 시간 정각에 실행
    public void finishAuctionsByTime() {
        LocalDateTime now = LocalDateTime.now();
        Optional<List<AuctionProgressItem>> completedItemsOptional = auctionProgressItemRepo.findAllByBidFinishTimeBefore(now);

        if (completedItemsOptional.isPresent()) {
            List<AuctionProgressItem> completedItems = completedItemsOptional.get();
            completedItems.forEach(this::transferItemToComplete);
        } else {
            log.info("현재 경매 완료된 물품이 존재하지 않습니다.", now);
        }
    }//end transferCompletedAuctions()

    private void transferItemToComplete(AuctionProgressItem item) {
        try {
            boolean isComplete = checkBidCompletionStatus(item);
            AuctionCompleteItem completeItem = buildAuctionCompleteItem(item, isComplete);

            Item auctionItem = item.getItem();
            auctionItem.endAuction();

            itemRepository.save(auctionItem);
            auctionCompleteItemRepo.save(completeItem);
            auctionProgressItemRepo.delete(item);
        } catch (CommonException e) {
            log.error("에러 발생 물품 {}: {}", item.getAuctionProgressItemId(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("에러 발생 물품 {}: {}", item.getAuctionProgressItemId(), e.getMessage());
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }//end transferItemToComplete()

    private boolean checkBidCompletionStatus(AuctionProgressItem item) throws CommonException {
        boolean maxPersonNickNameIsNull = item.getMaxPersonNickName() == null;
        boolean userIsNull = item.getUser() == null;

        if (maxPersonNickNameIsNull && userIsNull) {
            return false;
        } else if (maxPersonNickNameIsNull || userIsNull) {
            log.error("{}: 해당 경매 물품 입찰 정보가 유효하지 않습니다.", item.getAuctionProgressItemId());
            throw new CommonException(ErrorCode.ITEM_BID_FIELD_MISMATCH);
        }
        return true;
    }//end checkBidCompletionStatus()

    private AuctionCompleteItem buildAuctionCompleteItem(AuctionProgressItem item, boolean isComplete) {
        return AuctionCompleteItem.builder()
                .item(item.getItem())
                .itemTitle(item.getItemTitle())
                .thumbnail(item.getThumbnail())
                .startPrice(item.getStartPrice())
                .buyNowPrice(item.getBuyNowPrice())
                .bidFinishTime(item.getBidFinishTime())
                .location(item.getLocation())
                .user(item.getUser())
                .maxPersonNickName(item.getMaxPersonNickName())
                .maxPrice(item.getMaxPrice())
                .isBidComplete(isComplete)
                .build();
    }//end buildAuctionCompleteItem()
}//end class