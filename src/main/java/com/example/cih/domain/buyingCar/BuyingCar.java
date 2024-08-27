package com.example.cih.domain.buyingCar;

import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime registerTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Enumerated(EnumType.STRING)
    private BuyCarStatus buyCarStatus;              // 구매 제안 결과

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="SELLINGCAR_ID")
    private SellingCar sellingCar;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="uId")
    private User user;

    private Boolean isActive;

    public void changePrice(int proposalPrice){
        this.proposalPrice = proposalPrice;
    }

    public void changeBuyCarStatus(BuyCarStatus buyCarStatus){

        if(buyCarStatus == BuyCarStatus.PROPOSE_CANCEL
                || buyCarStatus == BuyCarStatus.CONSULT_CANCEL
                || buyCarStatus == BuyCarStatus.AUCTION_CANCEL )
        {
            isActive = false;
        }
        this.buyCarStatus = buyCarStatus;
    }
}
