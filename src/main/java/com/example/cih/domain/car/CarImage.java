package com.example.cih.domain.car;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "car")
@Table(name="carImages")
public class CarImage implements Comparable<CarImage> { // @OneToMany 처리에서 순번에 맞게 정렬하기 위해서
    @Id
    private String uuid;

    private String fileName;

    private int imageOrder;

    @ManyToOne
    @JoinColumn(name="carId")
    private Car car;

    @Override
    public int compareTo(CarImage o) {
        return this.imageOrder - o.imageOrder;
    }
    public void changeCar(Car car){
        this.car = car;
    }
}
