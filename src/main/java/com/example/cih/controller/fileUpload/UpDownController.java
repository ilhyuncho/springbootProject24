package com.example.cih.controller.fileUpload;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UpDownController {

    @Value("${com.cih.upload.path}")
    private String uploadPath;
    @ApiOperation(value="Upload Post", notes="POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO){
        log.error("==================Upload Post=====================");
        log.error(uploadFileDTO);

        List<UploadResultDTO> listResult = new ArrayList<>();

        if(uploadFileDTO.getFiles() != null){
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalFileName = multipartFile.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                boolean bImage = false;

                Path savePath = Paths.get(uploadPath, uuid+"_"+originalFileName);

                try {
                    multipartFile.transferTo(savePath); // 파일 저장


                    // 이미지 파일 이라면 ( 썸네일 생성 )
                    if (Files.probeContentType(savePath).startsWith("image")) {
                        log.error(Files.probeContentType(savePath).toString());

                        File thumbFile = new File(uploadPath,  "s_" + uuid + "_" + originalFileName);

                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200,200);

                        bImage = true;
                    }
                    
                    // 결과값 반환
                    listResult.add(UploadResultDTO.builder().uuid(uuid).fileName(originalFileName)
                            .img(bImage).build());


                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return listResult;
    }
}