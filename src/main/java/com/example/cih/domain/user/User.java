package com.example.cih.domain.user;


import com.example.cih.domain.car.Car;
import com.example.cih.dto.user.UserAddressReqDTO;
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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uId")
    private Long userId;

    @Column(name="userName", length = 10, nullable = false)
    private String userName;

    private UserGradeType mGrade;     // 등급
    private Integer mPoint;     // 획득 포인트

    @Embedded   // 임베디드 타입 (복합값)
    private Address address;

    @Embedded
    @AttributeOverride(name = "city.zipcode", column = @Column(name="billing_zipcode"))  // 임베드된 클래스의 프로퍼티 매핑을 재정의
    @AttributeOverride(name = "city.country", column = @Column(name="billing_coutry"))
    @AttributeOverride(name = "city.cityName", column = @Column(name="billing_cityName"))
    @AttributeOverride(name = "street", column = @Column(name="billing_street"))
    @AttributeOverride(name = "detailAddress", column = @Column(name="billing_detailAddress"))
    private Address billingAddress;


    @OneToMany(mappedBy = "user"        // 반대쪽 매핑의 필드 이름 값
            , fetch = FetchType.LAZY    // 지연 로딩 설정
            , cascade = CascadeType.ALL     // 자식 엔티티도 같이 저장 되도록
            , orphanRemoval = true          //부모 엔티티가 자식 엔티티와의 관계를 제거하면 자식은 고아로 취급되어 그대로 사라진다.
    )                                       // 추가적으로 부모가 삭제 되면 자식도 삭제 됨

    @Builder.Default        // 빌더로 인스턴스 생성 시 초기화할 값을 정할 수 있음.
    private List<Car> ownCars = new ArrayList<>();  // 고객 소유 자동차 list
//    @OrderBy("carYears desc")   // set은 순서 지정이 x, @OrderBy를 사용하면 정렬 가능
//    private Set<Car> ownCars = new HashSet<Car>();

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

      public void addMPoint(Integer point){
          this.mPoint += point;
      }

      public void minusMPoint(Integer point){
          this.mPoint -= point;
      }

      public void registerMainAddress(UserAddressReqDTO userAddressReqDTO){

          City city = new City(userAddressReqDTO.getZipcode(),
                  userAddressReqDTO.getCityName(),
                  userAddressReqDTO.getCountry());

          this.address = Address.builder()
                  .city(city)
                  .street(userAddressReqDTO.getStreet())
                  .detailAddress(userAddressReqDTO.getDetailAddress())
                  .build();
      }
}
