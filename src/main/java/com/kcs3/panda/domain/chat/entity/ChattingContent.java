package com.kcs3.panda.domain.chat.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChattingContent extends BaseEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ChattingRoom chattingRoom;

    private Long chatUserId;

    private String chatContent;

    @Builder
    public ChattingContent(ChattingRoom chattingRoom,Long chatUserId, String chatContent){
        this.chattingRoom = chattingRoom;
        this.chatUserId = chatUserId;
        this.chatContent = chatContent;
    }


}
