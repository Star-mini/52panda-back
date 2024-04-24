package com.kcs3.panda.domain.chat.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChattingRoom extends BaseEntity {

    private Long buyerId;
    private Long sellerId;

    @OneToMany(mappedBy = "chattingRoom", cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChattingContent> chattingContents = new ArrayList<>();
}
