package com.example.cih.domain.car;


import com.example.cih.common.CarSizeConverter;
import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.user.User;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Cars")
@Log4j2
// 부모에게 상속받은 컬럼명 변경 시
//@AttributeOverride(name="regDate", column = @Column(name="REG_DATE"))
public class Car extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="carId")
    private Long carId;

    @Column(name="carNumber", length = 50, nullable = false)
    private String carNumber;

    @Column(name="carGrade", length = 4, nullable = false)
    @Convert(converter= CarSizeConverter.class)
    private CarSize carGrade;

    @Column(name="carModel", length = 20, nullable = false)
    private String carModel;

    @Column(name="carYears", nullable = false)
    private int carYears;

    @Column(name="carColors", length = 10, nullable = false)
    private String carColors;

    @Column(name="carKm", nullable = false)
    private Long carKm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uId")
    private User user;


    @Builder.Default
    @ElementCollection  // Entity 생성을 어노테이션으로 지정 가능
    @CollectionTable(
            name = "carImages", // 테이블 이름
            joinColumns = @JoinColumn(name = "carId"))  // 고유하지 않은 인덱스, primarykey(x)
    @Column(name = "carImage")  // carImages 테이블의 이미지 피일이름이 저장될 컬럼명
    private Set<String> carImages = new HashSet<>();


    public void setUser(User user) {
        this.user = user;
    }

    // 고객 정보를 id 에서 User 객체로 변경
    // @Column(name="userId", nullable = false)
    // private Long userId;
    @Builder(builderMethodName = "writeWithUserBuilder")
    public Car(String carNumber, CarSize carGrade, String carModel, int carYears,
                      String carColors, Long carKm, User user) {

        this.carNumber = carNumber;
        this.carGrade = carGrade;
        this.carModel = carModel;
        this.carYears = carYears;
        this.carColors = carColors;
        this.carKm = carKm;

        this.user = user;   // 대입 방법 확인해 보자!!!
    }


    // 엔티티 리스너 등록 방법 (3가지)
    // 1. 엔티티에 직접 적용
    // 2. 별도의 리스너 등록 ( @EntityListeners )
    // 3. 기본 리스너 사용 ( 모든 엔티티의 이벤트를 처리하려고

    // 이벤트 호출 순서 : 1.기본 리스너 2.부모 클래스 리스너  3.리스너, 4.엔티티
    
    // @ExcludeDefaultListeners : 기본 리스너 무시
    // @ExcludeSuperclassListeners : 상위 클래스 이벤트 리스너 무시

    // 1.엔티티 이벤트 리스너 지정
    @PrePersist
    public void prePersist(){
        // persist() 메소드를 호출해서 엔티티를 영속성 컨텍스트에 관리하기 직전에 호출
        log.error("Car-prePersist() carId: " + carId);
    }

    @PostPersist
    public void postPersist(){
        // flush나 commit 을 호출해서 엔티티를 DB에 저장한 직후
        log.error("Car-PostPersist() carId: " + carId);
    }
    @PostLoad
    public void postLoad(){
        // 엔티티가 영속성 컨텍스트에 조회된 직후 또는 refresh를 호출한 후 ( 2차 캐시에 저장되어 있어도 호출됨 )
        log.error("Car-PostLoad() carId: " + carId);
    }
    @PostRemove
    public void postRemove(){
        // flush나 commit을 호출해서 엔티티를 DB에 삭제한 직후에 호출
        log.error("Car-PostRemove() carId: " + carId);
    }


}
