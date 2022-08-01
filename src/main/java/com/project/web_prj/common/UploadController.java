package com.project.web_prj.common;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@Log4j2
public class UploadController {

    // upload-form.jsp로 포워딩하는 요청
    @GetMapping("/upload-form")
    public String uploadForm() {
        return "upload/upload-form";
    }


    // 파일 업로드 처리를 위한 요청
    // MultipartFile : 클라이언트가 전송한 파일 정보들을 담은 객체
    // ex) 원본 파일명, 파일 용량, 파일 컨텐츠 타입, 업로드한 운영체제에 대한 정보, 업로드한 시간 등등등을 포함한다.
    @PostMapping("/upload")
    public String upload(MultipartFile file) { 
        log.info("/upload POST! - {}", file);

        log.info("file-name: {}", file.getName());
        log.info("file-origin-name: {}", file.getOriginalFilename());
        log.info("file-size(): {}KB", (double) file.getSize() / 1024); // byte 단위로 나온다.
        log.info("file-type: {}", file.getContentType());
        System.out.println("=========================================================================");


        // 서버에 업로드파일 저장

        // 1-1. 업로드 파일 저장 경로
        String uploadPath = "E:\\sl_basic\\upload"; // 윈도우는 \ (역슬래시) , 리눅스는 / (정슬래시)


        // 1. 세이브파일 객체 생성
        // - 첫번째 파라미터는 파일 저장경로 지정, 두번째 파라미터는 파일명 지정
        File f = new File(uploadPath, file.getOriginalFilename());

        try {
            file.transferTo(f); // 이렇게 했더니 파일명이 중복이면 덮어 씌워버리네??? 문제군..
                                // 그래서 일단위 또는 분단위로 폴더를 새로 만들기도 하고
                                // 한 20자리의 문자열을 랜덤으로 생성해서 파일명으로 만들어주기도 한다.
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/upload-form";
    }
}
