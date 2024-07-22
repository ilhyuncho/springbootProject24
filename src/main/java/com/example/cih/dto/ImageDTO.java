package com.example.cih.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long imageId;
    private String uuid;
    private String fileName;
    private Integer imageOrder;
    private Boolean isMainImage;
}