package com.example.cih.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class ImageListDTO {
    @Builder.Default
    private List<ImageDTO> fileNames = new ArrayList<>();

    public void addImage(Long imageId, String uuid, String fileName, int imageOrder, Boolean isMainImage){
        ImageDTO imageDTO = ImageDTO.builder()
                .imageId(imageId)
                .uuid(uuid)
                .fileName(fileName)
                .imageOrder(imageOrder)
                .isMainImage(isMainImage)
                .build();
        fileNames.add(imageDTO);
    }
    public ImageDTO getMainImage(){
        return fileNames.stream()
                .filter(ImageDTO::getIsMainImage)
                .findFirst().orElse(null);
    }

    public List<ImageDTO> getListExceptMainImage(){
        return fileNames.stream()
                .filter(imageDTO -> !imageDTO.getIsMainImage())
                .sorted(Comparator.comparing(ImageDTO::getImageOrder))
                .collect(Collectors.toList());
    }

    public List<ImageDTO> getListAllImage(){
        return fileNames.stream()
                .sorted(Comparator.comparing(ImageDTO::getImageOrder))
                .collect(Collectors.toList());
    }


}