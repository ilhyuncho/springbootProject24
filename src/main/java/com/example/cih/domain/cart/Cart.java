package com.example.cih.domain.cart;


import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.shop.ItemOption;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="carts")
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cartId")
    private Long cartId;

    // @OneToOne, @ManyToOne : 기존 패치 전략은 즉시 로딩
    // @OneToMany, @ManyToMany : 기본 패치 전략은 지연 로딩
    // -> 따라서 @OneToOne, @ManyToOne 은 fetch = FetchType.LAZY로 설정해서 지연 로딩 전략을
    // 사용하도록 변경하자

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

    private Boolean isActive;   // 장바구니에 있는 상태


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "itemOptionId")
//    private ItemOption itemOption;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "itemOptionId")  // 이걸 설정하지 않으면 중간에 조인테이블 전략을 사용함
//    @Builder.Default
//    private List<ItemOption> itemOptions = new ArrayList<>();

    private Long itemOptionId1;
    private Long itemOptionId2;


    @Column(name = "IMPERIAL_WEIGHT")
    @ColumnTransformer(     // db 저장,불러올때 값 변환
            read = "IMPERIAL_WEIGHT / 2",
            write = "? * 2"
    )
    private double metricWeight;

    @CreationTimestamp      // 하이버네이트 제공 ( 요즘은 하이버네이트 구현체를 많이 안쓰는 추세 )
    @ColumnTransformer(     // db 저장,불러올때 값 변환
            write = "date_add(?, interval 5 DAY)"   // db 함수를 지정
    )
    private LocalDateTime expiredDate;    // 장바구니에 넣고 자동 취소 되는 시간

    @Version
    private Integer verison;    // 버전 관리 기능 (낙관적 락 사용)

    public void changeItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    public void changeIsActive(Boolean isActive){
        this.isActive = isActive;
    }
}
