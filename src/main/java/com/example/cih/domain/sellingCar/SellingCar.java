package com.example.cih.domain.sellingCar;


import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.user.User;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sellingCars")
@ToString(exclude = {"car", "user", "buyingCarSet"})
public class SellingCar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SELLINGCAR_ID")
    private Long sellingCarId;

    private SellType sellType;
    private SellingCarStatus sellingCarStatus;

    private int RequiredPrice;

    private LocalDateTime expiredDate;

    @OneToOne(mappedBy = "sellingCar")  // SellingCar DB 테이블에는 Car정보는 없음
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="uId")
    private User user;

    @Column(columnDefinition = "integer default 0", nullable = false)   // 근데 default 0이 적용이 안된다
    private Integer likeCount;          // 좋아요 횟수

    private Integer viewCount;          // 조회 횟수


    @OneToMany(mappedBy = "sellingCar", //
            cascade = {CascadeType.ALL}, // SellingCar 엔티티에서 하위 엔티티 객체들을 관리 하는 기능을 추가 해서 사용
            fetch = FetchType.LAZY,
            orphanRemoval = true        // 하위 엔티티가 참조가 더 이상 없는 상태면 삭제 처리 해준다
    )
    @Builder.Default
    @BatchSize(size=20) // N번에 해당하는 쿼리를 모아서 한번에 실행, (N+1문제 해결)
    private Set<BuyingCar> buyingCarSet = new HashSet<>();

    public void changeStatus(SellingCarStatus sellingCarStatus){
        this.sellingCarStatus = sellingCarStatus;
    }

    public void changeLikeCount(Boolean isLike){
        if(isLike){
            likeCount +=1;
        }
        else{
            likeCount = likeCount > 0 ? likeCount - 1 : 0;
        }
    }
    public void changeViewCount(){
        viewCount +=1;
    }

}
