package com.example.cih.domain.cart;


import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.shop.ItemOption;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.user.User;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemOptionId")
    private ItemOption itemOption;

    public void changeItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
