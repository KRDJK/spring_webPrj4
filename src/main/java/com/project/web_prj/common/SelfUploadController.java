package com.project.web_prj.common;

import com.project.web_prj.util.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
public class SelfUploadController {

    private static final String UPLOAD_PATH = "E:\\sl_basic_kdj\\upload";


    @GetMapping("/self-upload")
    public String forward() {
        return "upload/upload";
    }


    @PostMapping("/upload-to")
    public String uploadFile(MultipartFile file) {
        log.info("/upload-to POST!! - {}", file);

        log.info(file.getName());
        log.info(file.getOriginalFilename());
        log.info(file.getContentType());
        log.info(file.getOriginalFilename());
        log.info(file.getSize() / (double) 1024 + "KB");


        FileUtils.uploadFile(file, UPLOAD_PATH);


        return "redirect:/self-upload";
    }


    @PostMapping("/ajax-self-upload")
    @ResponseBody
    public List<String> ajaxUploadFile(List<MultipartFile> files) { // formData를 통해 files 배열이 들어온다.

        log.info("/ajax-self-upload POST!! - {}", files.get(0).getOriginalFilename());

        List<String> fileNames = new ArrayList<>();


        for (MultipartFile file : files) {

            String fullPath = FileUtils.uploadFile(file, UPLOAD_PATH);

            fileNames.add(fullPath);
        }

        return fileNames;
    }


    @GetMapping("/loadingFile")
    @ResponseBody
    public ResponseEntity<byte[]> loadingFile(String fileName) {
        log.info("/loadingFile GET!! - {}", fileName);

        File f = new File(UPLOAD_PATH + fileName); // 1. 클라이언트에서 요청한 이미지 파일을 f에 담았다.

        if (!f.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        // 2. 해당 파일을 InputStream을 통해 불러온다.
        try (FileInputStream fis = new FileInputStream(f)) {

            // 3. 클라이언트에게 순수 이미지 데이터를 응답해야 하므로 MIME TYPE 을 응답 헤더에 설정하기 위한 작업.
            String ext = FileUtils.getFileExtension(fileName);

            MediaType mediaType = FileUtils.getMediaType(ext);


            // 4. 응답 헤더에 미디어 타입 설정
            HttpHeaders headers = new HttpHeaders();

            if (mediaType != null) {
                headers.setContentType(mediaType);
            }


            // 5. 첨부 파일의 순수 데이터를 바이트 배열에 저장
            byte[] rawData = IOUtils.toByteArray(fis);


            return new ResponseEntity<>(rawData, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
