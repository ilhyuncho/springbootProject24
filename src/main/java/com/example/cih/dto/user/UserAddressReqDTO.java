package com.example.cih.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressReqDTO {

    private String zipcode;
    private String cityName;
    private String country;
    private String street;
    private String detailAddress;
}
