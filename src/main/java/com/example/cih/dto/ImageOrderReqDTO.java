package com.example.cih.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageOrderReqDTO {

    private Long objectId;

    private List<ImageDTO> imageOrderList;

}
