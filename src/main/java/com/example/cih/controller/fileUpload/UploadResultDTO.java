package com.example.cih.controller.fileUpload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private boolean img;

    public String getLink(){
        if(img){
            return "s_" + uuid + "_" + fileName;
        }else{
            return uuid+"_"+fileName;
        }
    }
}
