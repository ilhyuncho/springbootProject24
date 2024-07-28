package com.example.cih.domain.user;


import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@AllArgsConstructor
@Builder
public class Address implements Serializable {

    //@NotEmpty
    private City city;

    //@NotEmpty
    private String street;

    //@NotEmpty
    private String detailAddress;

    public String fullAddress(){
        return city.getCountry() + " " + city.getCityName() + " " + street + " " + detailAddress;
    }

// 같을 기준으로 인스턴스를 비교하려면 필요 함
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return super.equals(obj);
//    }


}
