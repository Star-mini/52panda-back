package com.kcs3.panda.domain.auction.entity;

import com.kcs3.panda.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "ItemImage")
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
