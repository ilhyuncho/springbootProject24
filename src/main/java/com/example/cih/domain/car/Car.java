package com.example.cih.domain.car;


import com.example.cih.common.CarSizeConverter;
import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Cars")
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

    @ManyToOne
    @JoinColumn(name="uId")
    private User user;

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


}
