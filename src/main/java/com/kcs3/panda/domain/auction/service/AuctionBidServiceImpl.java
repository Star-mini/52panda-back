package com.kcs3.panda.domain.auction.service;

import com.kcs3.panda.domain.auction.entity.AuctionInfo;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.domain.auction.repository.AuctionInfoRepository;
import com.kcs3.panda.domain.auction.repository.AuctionProgressItemRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import com.kcs3.panda.domain.user.repository.UserRepository;
import com.kcs3.panda.domain.auction.dto.AuctionBidHighestDto;
import com.kcs3.panda.global.exception.CommonException;
import com.kcs3.panda.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuctionBidServiceImpl implements AuctionBidService {
    @Autowired
    private AuctionProgressItemRepository auctionProgressItemRepo;
    @Autowired
    private AuctionInfoRepository auctionInfoRepo;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean attemptBid(Long itemId, Long userId, String nickname, int bidPrice) {
        AuctionProgressItem progressItem = auctionProgressItemRepo.findByItemId(itemId)
                .orElseThrow(() -> new CommonException(ErrorCode.ITEM_NOT_FOUND));

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
        updateAuctionProgressItemMaxBid(progressItem, nickname, bidPrice);
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

    private void updateAuctionProgressItemMaxBid(AuctionProgressItem progressItem, String nickname, int price) {
        progressItem.updateAuctionMaxBid(nickname, price);
        auctionProgressItemRepo.save(progressItem);
    }//end updateAuctionProgressItemMaxBid()
}//end class