package com.example.cih.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ImageListDTO {
    @Builder.Default
    private List<ImageDTO> fileNames = new ArrayList<>();

    public void addImage(String uuid, String fileName, int imageOrder, Boolean isMainImage){
        ImageDTO imageDTO = ImageDTO.builder()
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
}