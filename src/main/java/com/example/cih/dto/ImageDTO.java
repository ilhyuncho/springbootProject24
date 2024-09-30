package com.example.cih.dto;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor          // Spring Data JPA가 직접 객체를 만드는 경우에 사용하기 위해서 추가
@Getter
public class ImageDTO {
    private Long imageId;
    private String uuid;
    private String fileName;
    private Integer imageOrder;
    private Boolean isMainImage;
}