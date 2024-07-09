package com.example.cih.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressBookReqDTO {
    private String deliveryName;
    private String recipientName;
    private String recipientPhoneNumber;
    private String deliveryRequest;

    private String zipcode;
    private String cityName;
    private String country;
    private String street;
    private String detailAddress;
}
