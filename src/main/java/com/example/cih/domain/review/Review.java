package com.example.cih.domain.review;

import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.shop.ShopItem;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Reviews")
@ToString
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reviewId")
    private Long reviewId;

    private String reviewText;
    private String reviewer;

    private int score;

    private Long orderId;
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="SHOP_ITEM_ID")
    private ShopItem shopItem;

}
