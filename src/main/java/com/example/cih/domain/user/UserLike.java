package com.example.cih.domain.user;


import com.example.cih.domain.sellingCar.SellingCar;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="UserLikes")
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userLikeId")
    private Long userLikeId;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="SELLINGCAR_ID")
    private SellingCar sellingCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uId")
    private User user;

    private Boolean isLike;

    public void changeLike(Boolean isLike){
        this.isLike = isLike;
    }

}
