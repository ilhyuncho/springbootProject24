package com.example.cih.controller.fileUpload;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

        // Stream.generate 활용 예제
        //Stream<UUID> uuid1 = Util.createUUID(4);
        //uuid1.forEach(log::error);

        if (uploadFileDTO.getFiles() != null) {
            uploadFileDTO.getFiles().forEach(multipartFile -> {

                String originalFileName = multipartFile.getOriginalFilename();
                assert originalFileName != null;

                String uuid = UUID.randomUUID().toString();
                String formatName = originalFileName.split("\\.")[1];
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalFileName);

                boolean bImage = false;
                try {
                    if (uploadFileDTO.getIsResize()){
                        createResizeFile(multipartFile, savePath, formatName);
                    }
                    else{
                        createOriginalFile(multipartFile, savePath);
                    }

                    // 이미지 파일 이라면 ( 썸네일 생성 )
                    if (Files.probeContentType(savePath).startsWith("image")) {

                        createThumbnail(savePath, uuid, originalFileName);
                        bImage = true;
                    }

                    // 결과값 반환
                    listResult.add(UploadResultDTO.builder().uuid(uuid).fileName(originalFileName)
                            .img(bImage).build());

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return listResult;
    }

    // 리사이즈 로 저장
    void createResizeFile(MultipartFile multipartFile, Path savePath, String formatName) throws Exception {

        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        bi = resizeImage(bi, 300, 300);

        ImageIO.write(bi, formatName, savePath.toFile());
    }
    void createOriginalFile(MultipartFile multipartFile, Path savePath) throws IOException {

        multipartFile.transferTo(savePath); // 파일 저장
    }

    void createThumbnail(Path savePath, String uuid, String originalFileName) throws IOException {

        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalFileName);
        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 100, 100);
    }
    // 가져온 이미지 리사이징 해주는 메서드
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
        // resize에 들어가는 속성을 변경해서 여러 모드로 변경해줄수있다
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
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

        // 임시로
        try {
            log.error("sleep start");
            Thread.sleep(3000); //1초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        Optional<Map<String, Boolean>> result = removeFiles(resource);

        return ResponseEntity.ok().body(result.get());
    }

    Optional<Map<String, Boolean>> removeFiles(Resource resource){
        // 예외를 발생시키는 대신에 Optional을 사용
        Map<String, Boolean> result = new HashMap<>();

        if(resource.exists()) {
            try {
                //throw new IOException();
                boolean delete = resource.getFile().delete();
                result.put("removed", delete);

                if (Files.probeContentType(resource.getFile().toPath()).startsWith("image")) {
                    File thumbFile = new File(uploadPath, "s_" + resource.getFilename());
                    boolean deleteThumbFile = thumbFile.delete();
                    result.put("thumbFile removed", deleteThumbFile);
                }

            } catch (IOException e) {
                result.put("removed", false);
                //return Optional.empty();      // null 이 아닌 객체를 반환
            }
        }
        else{
            result.put("removed", false);
        }
        return Optional.of(result);
    }
}