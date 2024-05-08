package com.example.cih.domain.sellingCar;


import com.example.cih.domain.car.Car;
import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}
