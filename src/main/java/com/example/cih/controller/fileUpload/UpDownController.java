package com.example.cih.controller.fileUpload;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UpDownController {

    @Value("${com.cih.upload.path}")
    private String uploadPath;

    @ApiOperation(value = "Upload Post", notes = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO) {
        log.error("==================Upload Post=====================");
        log.error(uploadFileDTO);

        List<UploadResultDTO> listResult = new ArrayList<>();

        if (uploadFileDTO.getFiles() != null) {
            uploadFileDTO.getFiles().forEach(multipartFile -> {
                String originalFileName = multipartFile.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                boolean bImage = false;

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

    @ApiOperation(value = "view 파일", notes = "GET방식으로 첨부파일 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName){

        //log.error("fileName:" + fileName);
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @ApiOperation(value="remove 파일", notes="Delete 방식으로 파일 삭제")
    @DeleteMapping("/remove/{fileName}")
    public ResponseEntity<Map<String, Boolean>> deleteFile(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        Map<String, Boolean> result = new HashMap<>();
        try{
            log.error("file 이 존재 하지 않아도 이부분 까지 진입 함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            
            if(resource.exists()){
                log.error("file 존재함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                boolean delete = resource.getFile().delete();
                result.put("removed", delete);

                if(Files.probeContentType(resource.getFile().toPath()).startsWith("image")){
                    File thumbFile = new File(uploadPath, "s_" + resource.getFilename());
                    boolean deleteThumbFile = thumbFile.delete();
                    result.put("thumbFile removed", deleteThumbFile);
                }

                return ResponseEntity.ok().body(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        result.put("removed", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
















}