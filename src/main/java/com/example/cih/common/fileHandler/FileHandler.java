package com.example.cih.common.fileHandler;

import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.controller.fileUpload.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class FileHandler {


    @Value("${com.cih.upload.path}")
    private String uploadPath;


    public List<UploadResultDTO> fileUpload(UploadFileDTO uploadFileDTO){

        List<UploadResultDTO> listResult = new ArrayList<>();

        if (uploadFileDTO.getFiles() != null) {
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalFileName = multipartFile.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                boolean bImage = false;

                log.error("originalFileName:" + originalFileName);
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalFileName);

                try {
                    multipartFile.transferTo(savePath); // 파일 저장


                    // 이미지 파일 이라면 ( 썸네일 생성 )
                    if (Files.probeContentType(savePath).startsWith("image")) {
                        log.error(Files.probeContentType(savePath).toString());

                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalFileName);

                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);

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
