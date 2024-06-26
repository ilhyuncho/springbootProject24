package com.example.cih.dto.shop;


import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class ItemOptionDTO {
    private String optionType;
    private String optionValue;
}
