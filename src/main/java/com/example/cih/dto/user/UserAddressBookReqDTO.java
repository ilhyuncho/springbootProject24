package com.example.cih.dto.user;


import com.example.cih.domain.user.Address;
import com.example.cih.domain.user.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressBookReqDTO {
    private Long userAddressBookId;
    private String deliveryName;
    private String recipientName;
    private String recipientPhoneNumber;
    private String deliveryRequest;

    private String zipcode;
    private String cityName;
    private String country;
    private String street;
    private String detailAddress;
    private String callType;            // register or modify

    public Address generateAddress(){
        City city = City.builder()
                .zipcode(zipcode)
                .country(country)
                .cityName(cityName)
                .build();

        return Address.builder()
                .city(city)
                .street(street)
                .detailAddress(detailAddress)
                .build();
    }


}
