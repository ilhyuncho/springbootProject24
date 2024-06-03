package com.example.cih.dto.notification;


import com.example.cih.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NotificationResDTO {
    private Long notiId;
    private String name;
    private String title;
    private String message;
    private LocalDate expiredDate;

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
