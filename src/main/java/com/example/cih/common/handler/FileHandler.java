package com.example.cih.common.handler;

import com.example.cih.controller.fileUpload.UploadFileDTO;
import com.example.cih.controller.fileUpload.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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


    @Value("${com.cih.upload.path}")        // 초기 환경 객체를 호출하는 방법
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

    public void removeFiles(List<String> files){

        log.error("removeFiles() 호출");

        files.forEach(log::error);

        for (String fileName : files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try{
                String contentType = Files.probeContentType(resource.getFile().toPath());

                boolean delete = resource.getFile().delete();
                
                // 섬네일이 존재한다면
                if(contentType.startsWith("image")){
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);

                    boolean deleteThumbnail = thumbnailFile.delete();
                }
            }catch(Exception e){
                log.error(e.getMessage());
            }
        }
    }

}
