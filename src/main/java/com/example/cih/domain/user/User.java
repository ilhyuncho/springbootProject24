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

    @Column(name="address",length = 100, nullable = false)
    private String address;

    @OneToMany(mappedBy = "user")       // 반대뽁 매핑의 필드 이름 값
    private List<Car> ownCars = new ArrayList<>();  // 고객 소유 자동차 list

}
