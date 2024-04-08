package com.example.cih.domain.user;


import com.example.cih.domain.car.Car;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "ownCars")
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uId")
    private Long userId;

    @Column(name="userName", length = 10, nullable = false)
    private String userName;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")       // 반대뽁 매핑의 필드 이름 값
                                        // mappedBy를 소유 한것은 주인이 아니다
    private List<Car> ownCars = new ArrayList<>();  // 고객 소유 자동차 list

//    @OneToOne
//    @JoinColumn(name="userCreditsId")   // pk(외래키)가 user테이블(주테이블)에 생성
//    private UserCredit userCredit;

//    @OneToOne(mappedBy = "userCredit")  // 일대일-양방향에서 주인이 아니다
//    private UserCredit userCredit;



}
