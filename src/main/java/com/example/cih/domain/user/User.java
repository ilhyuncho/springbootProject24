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

    @OneToMany(mappedBy = "user"        // 반대쪽 매핑의 필드 이름 값
            , fetch = FetchType.LAZY    // 지연 로딩 설정
            , cascade = CascadeType.ALL     // 자식 엔티티도 같이 저장 되도록
    )
    @Builder.Default        // 빌더로 인스턴스 생성 시 초기화할 값을 정할 수 있음.
    private List<Car> ownCars = new ArrayList<>();  // 고객 소유 자동차 list

//    @OneToOne
//    @JoinColumn(name="userCreditsId")   // pk(외래키)가 user테이블(주테이블)에 생성
//    private UserCredit userCredit;

//    @OneToOne(mappedBy = "userCredit")  // 일대일-양방향에서 주인이 아니다
//    private UserCredit userCredit;

      public void addMyCars(Car car){

          if( !ownCars.contains(car) ){
              ownCars.add(car);
          }
          car.setUser(this);

      }

}
