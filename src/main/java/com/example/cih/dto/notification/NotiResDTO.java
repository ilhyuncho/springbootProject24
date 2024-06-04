package com.example.cih.dto.notification;


import com.example.cih.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NotiResDTO {
    private Long notiId;
    private String name;
    private String title;
    private String message;
    private LocalDate regDate;

    @Builder.Default
    private List<ImageDTO> fileNames = new ArrayList<>();
    public void addImage(String uuid, String fileName, int imageOrder){
        ImageDTO image = ImageDTO.builder()
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .build();
        fileNames.add(image);
    }

}
