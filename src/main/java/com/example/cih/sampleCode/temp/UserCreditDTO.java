package com.example.cih.sampleCode.temp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditDTO {
    private Long sampleID;
    private String sampleName;
    private String ex1;

}
