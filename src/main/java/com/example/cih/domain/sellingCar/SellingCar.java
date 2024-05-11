package com.example.cih.domain.sellingCar;


import com.example.cih.domain.buyingCar.BuyingCar;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

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
public class SellingCar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SELLINGCAR_ID")
    private Long sellingCarId;

    private SellingCarStatus sellingCarStatus;

    private int RequiredPrice;

    @CreationTimestamp
    @ColumnTransformer(     // db 저장,불러올때 값 변환
            write = "date_add(?, interval 7 DAY)"   // db 함수를 지정
    )
    private LocalDateTime expiredDate;

    @OneToOne(mappedBy = "sellingCar")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="uId")
    private User user;

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


}
