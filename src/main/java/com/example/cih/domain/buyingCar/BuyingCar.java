package com.example.cih.domain.buyingCar;

import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="buyingCars")
public class BuyingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BUYING_CAR_ID")
    private Long buyingCarId;

    private int proposalPrice;

    @CreationTimestamp
    private LocalDateTime registerDate;

    @Enumerated(EnumType.STRING)
    private BuyResult buyResult;              // 구매 제안 결과

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="SELLINGCAR_ID")
    private SellingCar sellingCar;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="uId")
    private User user;

    public void changePrice(int proposalPrice){
        this.proposalPrice = proposalPrice;
    }
}
