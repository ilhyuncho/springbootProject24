package com.example.cih.sampleCode.temp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    private Long sellerId;
    private String userId;
    private Long bankAccount;

}
