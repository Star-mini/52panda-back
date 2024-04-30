package com.kcs3.panda.domain.auction.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kcs3.panda.domain.auction.dto.AuctionItemRequest;
import com.kcs3.panda.domain.auction.dto.CommentRequest;
import com.kcs3.panda.domain.auction.dto.QnaPostRequest;
import com.kcs3.panda.domain.auction.entity.AuctionCompleteItem;
import com.kcs3.panda.domain.auction.entity.AuctionProgressItem;
import com.kcs3.panda.domain.auction.entity.Item;
import com.kcs3.panda.domain.auction.entity.ItemDetail;
import com.kcs3.panda.domain.auction.entity.ItemImage;
import com.kcs3.panda.domain.auction.entity.ItemQuestion;
import com.kcs3.panda.domain.auction.entity.QnaComment;
import com.kcs3.panda.domain.auction.repository.AuctionCompleteItemRepository;
import com.kcs3.panda.domain.auction.repository.AuctionProgressItemRepository;
import com.kcs3.panda.domain.auction.repository.ItemDetailRepository;
import com.kcs3.panda.domain.auction.repository.ItemQuestionRepository;
import com.kcs3.panda.domain.auction.repository.ItemRepository;
import com.kcs3.panda.domain.auction.repository.QnaCommentRepository;
import com.kcs3.panda.domain.user.entity.User;
import com.kcs3.panda.domain.user.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final AmazonS3Client amazonS3Client;
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

    public void postQna(QnaPostRequest request, Long itemId){

        ItemQuestion itemQuestion = new ItemQuestion();
        itemQuestion.setItemDetail(this.findDetailByItemId(itemId));
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
        comment.setItemQuestion(this.findItemQuestionById(questionId));
        comment.setComment(request.getComment());
        this.qnaCommentRepository.save(comment);
    }
    public void deleteComment(Long questionId)
    {
        ItemQuestion itemQuestion = this.findItemQuestionById(questionId);
        if (itemQuestion!=null) {
            this.itemQuestionRepository.delete(itemQuestion);
        }
    }







    public void postItem(AuctionItemRequest request) throws IOException {
        AuctionProgressItem auctionProgressItem= new AuctionProgressItem();
        auctionProgressItem.setItemTitle(request.title);
        auctionProgressItem.setBidFinishTime(request.finish_time);
        auctionProgressItem.setStartPrice(request.start_price);
        auctionProgressItem.setBuyNowPrice(request.buy_now_price);
        //썸네일저장.
        if (!request.images.isEmpty()) { // 이미지 리스트가 비어있지 않은지 확인
            MultipartFile file = request.images.get(0); // 첫 번째 이미지를 가져옴
            // 파일을 저장하고 URL을 생성하는 로직 필요
            String thumbnailUrl = ""; // 임시로 빈 문자열, 실제 로직에 맞게 수정 필요

            // 생성된 URL을 thumbnail로 설정
            auctionProgressItem.setThumbnail(thumbnailUrl);
        }


        User user =userRepository.findByUserId(1L);
        Item item = new Item();
        item.setCategory(request.category);
        item.setTradingMethod(request.trading_method);
        item.setAuctionComplete(false);
        item.setSeller(user);

        auctionProgressItem.setItem(item);

        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setItem(item);
        itemDetail.setItemDetailContent(request.contents);

        ItemImage itemImage = new ItemImage();
        itemImage.setItemDetail(itemDetail);
        itemImage.setImageUrls(this.saveFiles(request.images));


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
}