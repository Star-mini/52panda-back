package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "ItemImage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class ItemImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemImage", nullable = false)
    private Long itemImage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemDetailId")
    private ItemDetail itemDetail;

    @Column(name = "url", nullable = false)
    private String url;  // URL을 저장하는 필드

    @Override
    public String toString() {
        return "ItemImage{" +
                "id=" + itemImage +  // BaseEntity에서 상속받은 ID
                ", url='" + url + '\'' +
                ", itemDetail=" + (itemDetail != null ? "ItemDetail[id=" + itemDetail.getItemDetailId() + "]" : "null") +
                '}';
    }
}
