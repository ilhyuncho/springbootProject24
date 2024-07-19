package com.example.cih.controller.fileUpload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadFileDTO {
    // 컨트롤러 param으로 MultipartFile로 지정하면 간단하지만 swagger 와 같은 프레임워크로
    // 테스트 하기 불편 하기 때문에 별도의 DTO로 선언 함
    private List<MultipartFile> files;
    private Boolean isResize = false;
}
