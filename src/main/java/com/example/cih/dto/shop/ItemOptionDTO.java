package com.example.cih.dto.shop;


import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class ItemOptionDTO {
    private Integer typePriority;
    private String optionType;
    private String optionValue;           // 예) 0-선택안함, 1-노란색, 2-파란색
    private String optionValueForView;    // 예) 노란색, 파란색
}
