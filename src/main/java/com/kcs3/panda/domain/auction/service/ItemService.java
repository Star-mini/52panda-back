package com.kcs3.panda.domain.auction.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kcs3.panda.domain.auction.dto.*;
import com.kcs3.panda.domain.auction.entity.*;
import com.kcs3.panda.domain.auction.repository.*;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.domain.user.repository.UserRepository;
import com.kcs3.panda.global.config.oauth.CustomOAuth2User;
import com.kcs3.panda.global.exception.CommonException;
import com.kcs3.panda.global.exception.ErrorCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service
@RequiredArgsConstructor
public class ItemService {
    private final AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.AP_NORTHEAST_2) // 서울 리전
            .build();
    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final AuctionProgressItemRepository auctionProgressItemRepository;
    @Autowired
    private final AuctionCompleteItemRepository auctionCompleteItemRepository;
    @Autowired
    private final ItemDetailRepository itemDetailRepository;
    @Autowired
    private final ItemQuestionRepository itemQuestionRepository;
    @Autowired
    private final QnaCommentRepository qnaCommentRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AlarmRepository alarmRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private  ItemImageRepository itemImageRepository;

    private final ObjectMapper objectMapper;

    public List<String> getAlarm(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        User user = userRepository.findByUserId(customOAuth2User.getUserId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<Alarm> alarms = alarmRepository.findTop4ByUserOrderByCreatedAtDesc(user);

        return alarms.stream()
                .map(Alarm::getAlarmContent)
                .collect(Collectors.toList());

    }
    public void postQna(QnaPostRequest request, Long itemId){

        ItemQuestion itemQuestion = new ItemQuestion();
        itemQuestion.setItemDetailId(this.findDetailByItemId(itemId));
        itemQuestion.setQuestionContents(request.getQuestionContents());
        itemQuestion.setQuestionUserId(request.getQuestionUserId());
        this.itemQuestionRepository.save(itemQuestion);
    }


    public void deleteQna(Long questionId)
    {
        ItemQuestion itemQuestion = this.findItemQuestionById(questionId);
        if (itemQuestion!=null) {
            this.itemQuestionRepository.delete(itemQuestion);
        }
    }





    public void postComment(CommentRequest request, Long questionId){

        QnaComment comment = new QnaComment();
        comment.setQuestionId(this.findItemQuestionById(questionId));
        comment.setComment(request.getComment());
        this.qnaCommentRepository.save(comment);
    }

    public boolean deleteComment(Long commentId) {
        QnaComment qnaComment = this.findCommentById(commentId);
        if (qnaComment != null) {
            this.qnaCommentRepository.delete(qnaComment);
            return true;
        }
        return false;
    }








    public void postItem(AuctionItemRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        User user = userRepository.findByUserId(customOAuth2User.getUserId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Region region = regionRepository.findByRegion("전체");
        if (region == null) {
            throw new RuntimeException("Region '전체' not found");
        }

        Item item = new Item();
        item.setCategory(request.category);
        item.setTradingMethod(request.trading_method);
        item.setAuctionComplete(false);
        item.setSeller(user);
        item.setRegion(region);

        AuctionProgressItem auctionProgressItem = new AuctionProgressItem();
        auctionProgressItem.setItemTitle(request.title);
        auctionProgressItem.setBidFinishTime(request.finish_time);
        auctionProgressItem.setStartPrice(request.start_price);

        // Null 체크 후 설정
        if (request.buy_now_price != null) {
            auctionProgressItem.setBuyNowPrice(request.buy_now_price);
        } else {
            auctionProgressItem.setBuyNowPrice(null); // null 값 설정
        }

        auctionProgressItem.setLocation("전체");
        auctionProgressItem.setItem(item);
        auctionProgressItem.setMaxPrice(request.start_price);

        ArrayList<String> imageUrls = this.saveFiles(request.images);
        if (!imageUrls.isEmpty()) {
            auctionProgressItem.setThumbnail(imageUrls.get(0));
        } else {
            throw new IOException("이미지가 제공되지 않았습니다.");
        }

        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setItem(item);
        itemDetail.setItemDetailContent(request.contents);

        List<ItemImage> itemImages = new ArrayList<>();
        for (String url : imageUrls) {
            ItemImage itemImage = new ItemImage();
            itemImage.setUrl(url);
            itemImage.setItemDetail(itemDetail);
            itemImages.add(itemImage);
        }

        itemDetail.setImages(itemImages);

        itemRepository.save(item);
        auctionProgressItemRepository.save(auctionProgressItem);
        itemDetailRepository.save(itemDetail);
        for (ItemImage itemImage : itemImages) {
            itemImageRepository.save(itemImage);
        }
    }

    //임베딩값을 위한 저장
    public Long getLastItemId() {
        return itemRepository.findTopByOrderByItemIdDesc().getItemId();
    }
    //임베딩값 저장하기
    public void updateEmbedding(Long itemId, double[] embedding) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + itemId));
        try {
            String embeddingJson = objectMapper.writeValueAsString(embedding);
            item.setEmbedding(embeddingJson);
            itemRepository.save(item);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize embedding", e);
        }
    }


//    이미지저장하고 url 반환

    private ArrayList<String> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        ArrayList<String> urls = new ArrayList<>();

        for(MultipartFile file:multipartFiles)
        {
            String fileName = file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
            urls.add(amazonS3Client.getUrl(bucket,fileName).toString());
        }
        return urls;
    }
    private ItemDetail findDetailByItemId(Long itemId){
        Optional<ItemDetail> OitemDetail = itemDetailRepository.findByItemId(itemId);
        if (OitemDetail.isPresent()) {
            return OitemDetail.get();
        }
        return null;
    }

    private ItemQuestion findItemQuestionById(long questionId){
        Optional<ItemQuestion> OitemQuestion= itemQuestionRepository.findById(questionId);
        if(OitemQuestion.isPresent())
        {
            return OitemQuestion.get();
        }
        return null;
    }

    private QnaComment findCommentById(long commentId) {
        Optional<QnaComment> optionalQnaComment = qnaCommentRepository.findById(commentId);
        return optionalQnaComment.orElse(null);
    }

    public ItemDetailRequestDto getItemDetail(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        AuctionProgressItem progressItem = auctionProgressItemRepository.findByItemItemId(itemId)
                .orElse(null);

        AuctionCompleteItem completeItem = null;
        if (progressItem == null) {
            completeItem = auctionCompleteItemRepository.findByItemItemId(itemId)
                    .orElseThrow(() -> new RuntimeException("AuctionProgressItem or AuctionCompleteItem not found for itemId: " + itemId));
        }

        ItemDetail itemDetail = itemDetailRepository.findByItemId(itemId)
                .orElseThrow(() -> new RuntimeException("ItemDetail not found for itemId: " + itemId));

        List<ItemQuestion> itemQuestions = itemQuestionRepository.findByItemDetailId_ItemDetailId(itemDetail.getItemDetailId());

        return toDTO(item, progressItem, completeItem, itemDetail, itemQuestions);
    }

    private ItemDetailRequestDto toDTO(Item item, AuctionProgressItem progressItem, AuctionCompleteItem completeItem, ItemDetail itemDetail, List<ItemQuestion> itemQuestions) {
        ItemDetailRequestDto dto = new ItemDetailRequestDto();
        dto.setItemId(item.getItemId());
        dto.setTitle(progressItem != null ? progressItem.getItemTitle() : completeItem.getItemTitle());
        dto.setBidFinishTime(progressItem != null ? progressItem.getBidFinishTime() : completeItem.getBidFinishTime());
        dto.setStartPrice(progressItem != null ? progressItem.getStartPrice() : completeItem.getStartPrice());
        dto.setMaxPrice((progressItem != null && progressItem.getMaxPersonNickName() == null) ? 0 :
                ((progressItem != null) ? progressItem.getMaxPrice() :
                        ((completeItem.getMaxPersonNickName() == null) ? 0 : completeItem.getMaxPrice())));

        // Optional을 사용하여 null 체크 및 값 설정
        Integer buyNowPrice = Optional.ofNullable(progressItem != null ? progressItem.getBuyNowPrice() : completeItem != null ? completeItem.getBuyNowPrice() : null)
                .orElse(null);
        dto.setBuyNowPrice(buyNowPrice);

        dto.setAuctionComplete(item.isAuctionComplete());
        dto.setItemCreateTime(item.getCreatedAt());
        dto.setSellerId(item.getSeller().getUserId());
        dto.setUserNickname(item.getSeller().getUserNickname());
        dto.setItemDetailContent(itemDetail.getItemDetailContent());
        dto.setCategoryName(item.getCategory().getCategory());

        dto.setImages(itemDetail.getImages().stream().map(image -> {
            ItemDetailRequestDto.ImageDTO imageDTO = new ItemDetailRequestDto.ImageDTO();
            imageDTO.setImageURL(image.getUrl());
            return imageDTO;
        }).collect(Collectors.toList()));

        dto.setQuestions(itemQuestions.stream().map(question -> {
            ItemDetailRequestDto.QuestionDTO questionDTO = new ItemDetailRequestDto.QuestionDTO();
            questionDTO.setQuestionId(question.getItemQuestionId());
            questionDTO.setQuestionContents(question.getQuestionContents());
            questionDTO.setQuestionTime(question.getCreatedAt());
            questionDTO.setComments(question.getComments().stream().map(comment -> {
                ItemDetailRequestDto.QuestionDTO.CommentDTO commentDTO = new ItemDetailRequestDto.QuestionDTO.CommentDTO();
                commentDTO.setCommentId(comment.getQnaCommentId());
                commentDTO.setCommentTime(comment.getCreatedAt());
                commentDTO.setComment(comment.getComment());
                return commentDTO;
            }).collect(Collectors.toList()));
            return questionDTO;
        }).collect(Collectors.toList()));

        return dto;
    }



    //플라스크에서 받은 아이템list  dto로 작성
    public List<RecommendDto> getItemsByIds(List<Long> itemIds) {
        List<AuctionProgressItem> auctionItems = auctionProgressItemRepository.findAllById(itemIds);
        return auctionItems.stream()
                .map(item -> new RecommendDto(
                        item.getItem().getItemId(),
                        item.getItemTitle(),
                        item.getThumbnail(),
                        item.getMaxPrice()))
                .collect(Collectors.toList());
    }


}