package com.example.cih.domain.car;


import com.example.cih.common.CarSizeConverter;
import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.user.User;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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

    // car 관점에서 첨부파일을 바라보는 @OneToMany ( pk를 가진 쪽에서 사용한다?? )
    @OneToMany(mappedBy = "car", // CarImage의 car변수
            cascade = {CascadeType.ALL}, // Car 엔티티에서 하위 엔티티 객체들을 관리 하는 기능을 추가 해서 사용
            fetch = FetchType.LAZY,
            orphanRemoval = true        // 하위 엔티티가 참조가 더 이상 없는 상태면 삭제 처리 해준다
    )
    @Builder.Default
    private Set<CarImage> imageSet = new HashSet<>();

    public void setUser(User user) {
        this.user = user;
    }

    //car 엔티티 에서 carImage 엔티티 객체들을 모두 관리  begin---------------
    public void addImage(String uuid, String fileName){
        CarImage carImage = CarImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .car(this)
                .imageOrder(imageSet.size())
                .build();
        imageSet.add(carImage);
    }

    public void clearImages(){
        imageSet.forEach(image -> image.changeCar(null));
        this.imageSet.clear();
    }
    //car 엔티티 에서 carImage 엔티티 객체들을 모두 관리  end---------------

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

    ////////////////////////// 학습용
    @Builder.Default
    @ElementCollection  // Entity 생성을 어노테이션으로 지정 가능
    @GenericGenerator(name="identity_gen", strategy = "sequence")
    @CollectionTable(
            name = "carTemps", // 테이블 이름
            joinColumns = @JoinColumn(name = "carId"))  // 고유하지 않은 인덱스, primarykey(x)
    @CollectionId(  // 대리키 생성
            columns = @Column(name = "carTempId"),
            type = @org.hibernate.annotations.Type(type="long"),
            generator = "identity_gen"
    )
    @Column(name = "carTemp")  // carTemps 테이블의 이미지 피일이름이 저장될 컬럼명
    private Collection<String> carTemps = new ArrayList<>();
    ////////////////////////// 학습용

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
