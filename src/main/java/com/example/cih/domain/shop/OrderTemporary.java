package com.example.cih.domain.shop;


import com.example.cih.domain.common.ItemOptionEntity;
import com.example.cih.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="orderTemporarys")
public class OrderTemporary extends ItemOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderTemporaryId")
    private Long orderTemporaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ITEM_ID")
    private ShopItem shopItem;

    @Column(name="itemCount", nullable = false)
    private int itemCount;

    @Column(name="discountPrice", nullable = false)
    private int discountPrice;

    @CreationTimestamp
    @ColumnTransformer(     // db 저장,불러올때 값 변환
            write = "date_add(?, interval 1 MINUTE)"   // db 함수를 지정
    )
    private LocalDateTime expiredDate;  // 만료 시간
}
